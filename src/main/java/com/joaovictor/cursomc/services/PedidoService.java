package com.joaovictor.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joaovictor.cursomc.domain.Cliente;
import com.joaovictor.cursomc.domain.ItemPedido;
import com.joaovictor.cursomc.domain.PagamentoComBoleto;
import com.joaovictor.cursomc.domain.Pedido;
import com.joaovictor.cursomc.domain.Produto;
import com.joaovictor.cursomc.domain.ProdutoVariacao;
import com.joaovictor.cursomc.domain.enums.EstadoPagamento;
import com.joaovictor.cursomc.repositories.ItemPedidoRepository;
import com.joaovictor.cursomc.repositories.PagamentoRepository;
import com.joaovictor.cursomc.repositories.PedidoRepository;
import com.joaovictor.cursomc.secutiry.UserSS;
import com.joaovictor.cursomc.services.exceptions.AuthorizationException;
import com.joaovictor.cursomc.services.exceptions.DataIntegrityException;
import com.joaovictor.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	
	@Autowired
	private BoletoService boletoService;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ProdutoVariacaoService produtoVariacaoService; 
	
	@Autowired
	private InterfaceEmailService interfaceEmailService;
	
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
	
	@Transactional
	public Pedido insert(Pedido obj) throws CloneNotSupportedException {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for (ItemPedido ip : obj.getItens()) {
			validaItemPedido(ip);
			ip.setDesconto(0.0);
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
			
			if (ip.getVariacao() != null) {
				produtoVariacaoService.atualizaQuantidade(ip.getVariacao(), ip.getQuantidade());
			}
			produtoService.atualizaQuantidade(ip.getProduto(), ip.getQuantidade());
		}
		
		
		itemPedidoRepository.saveAll(obj.getItens());
		// interfaceEmailService.sendOrderConfirmationEmail(obj);
		interfaceEmailService.sendOrderConfirmationHtmlEmail(obj);
		return obj;
	}
	
	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente =  clienteService.find(user.getId());
		return repo.findByCliente(cliente, pageRequest);
	}
	
	private void validaItemPedido(ItemPedido itemPedido) {
		Produto produto = itemPedido.getProduto();
		ProdutoVariacao produtoVariacao = itemPedido.getVariacao();
		Produto produtoAux = produtoService.find(produto.getId());
		
		if (produtoVariacao == null) {
			if (produtoAux.getVariacoes() != null && produtoAux.getVariacoes().size() > 0) {
				throw new DataIntegrityException("Variação ID: " + produtoAux.getId() + 
						" tem variações, defina uma varição para esse ItemProduto.Produto.");
			}
			
			if (itemPedido.getQuantidade() == null || itemPedido.getQuantidade() < 1) throw new DataIntegrityException("Variação ID: " + produtoAux.getId() + 
					" a quantidade deve ser maior que 0.");
			if (produtoAux.getQuantidade() < itemPedido.getQuantidade()) throw new DataIntegrityException("Variação ID: " + produtoAux.getId() + 
					" a quantidade passada ultrapassa o valor em estoque. Quantidade disponível: " + produtoAux.getQuantidade());
		} else {
			ProdutoVariacao produtoVariacaoAux = produtoVariacaoService.find(produtoVariacao.getId());
			if (!produtoVariacaoAux.getProduto().equals(produto)) throw new DataIntegrityException("Variação SKU: " + produtoVariacaoAux.getSku() + 
				" não pertenece ao produto ID: " + produto.getId() + ".");
			
			if (itemPedido.getQuantidade() == null || itemPedido.getQuantidade() < 1) throw new DataIntegrityException("Variação SKU: " + produtoVariacaoAux.getSku() + 
					" a quantidade deve ser maior que 0.");
			
			if (produtoVariacaoAux.getQuantidade() < itemPedido.getQuantidade()) throw new DataIntegrityException("Variação SKU: " + produtoVariacaoAux.getSku() + 
					" a quantidade passada ultrapassa o valor em estoque. Quantidade disponível: " + produtoVariacaoAux.getQuantidade());
		}
		
		
			
		
		
	}
}
