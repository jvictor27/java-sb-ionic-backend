package com.joaovictor.cursomc.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.joaovictor.cursomc.domain.Categoria;
import com.joaovictor.cursomc.domain.Opcao;
import com.joaovictor.cursomc.domain.Produto;
import com.joaovictor.cursomc.domain.ProdutoVariacao;
import com.joaovictor.cursomc.dto.CategoriaMostraPaiDTO;
import com.joaovictor.cursomc.repositories.CategoriaRepository;
import com.joaovictor.cursomc.repositories.ProdutoRepository;
import com.joaovictor.cursomc.services.exceptions.DataIntegrityException;
import com.joaovictor.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository repo;
	
	@Autowired
	private CategoriaRepository CatRepo;
	
	@Autowired
	private CategoriaService CatService;
	
	@Autowired
	private ProdutoVariacaoService produtoVariacaoService;
	
	@Autowired
	private OpcaoService opcaoService;
	
	public Produto find(Integer id) {
		Optional<Produto> obj = repo.findById(id);
		obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
		
		Produto produto = obj.get();
		produto = simplifyProdutoCategorias(produto);
		return produto;
	}
	
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String direction, String orderBy) {
		if (page > 0) {
			page--;
		}
		Page<Produto> produtos;
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = CatRepo.findAllById(ids);
		if (categorias != null && categorias.size() > 0) {
			produtos = repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);			
		} else {
			produtos = repo.findDistinctByNomeContaining(nome, pageRequest);
		}
		
		for (Produto produto : produtos) {
			produto = simplifyProdutoCategorias(produto);
		}
		
		return produtos;
	}
	
	private Produto simplifyProdutoCategorias(Produto obj) {
		if (obj.getCategorias() != null && obj.getCategorias().size() > 0) {
			for (Iterator<Categoria> categoriaIterator = obj.getCategorias().iterator(); categoriaIterator.hasNext();) {
				Categoria categoria = categoriaIterator.next();
				if (categoria.getNivel() != 4) {
					categoriaIterator.remove();
				}
			}
		}
		
		return obj;
	}
	
	public Produto insert(Produto obj) {
		
		if (obj.getCategorias() == null || obj.getCategorias().size() < 1) {
			throw new DataIntegrityException("Cadastre ao menos categoria para o produto");
		}
		
		List<Categoria> listaCategorias = new ArrayList<>();
		
		for (Categoria categoria : obj.getCategorias()) {
			Categoria findCategoria = CatService.find(categoria.getId());
			listaCategorias.add(findCategoria);
			listaCategorias.addAll(addArvoreCategoriaNoProduto(findCategoria));
		}
		
		obj.setCategorias(listaCategorias);
		
		Produto produto;
		
		if (obj.getVariacoes() != null && obj.getVariacoes().size() > 0) {
			Integer quantidade = 0;
			for (ProdutoVariacao variacao : obj.getVariacoes()) {
				if (produtoVariacaoService.produtoVariacaoCadastrada(variacao)) {
					throw new DataIntegrityException("Variacao com SKU: " + variacao.getSku() + " já cadastrada.");
				}
				
				if (variacao.getOpcoes() == null || variacao.getOpcoes().size() < 1) {
					throw new DataIntegrityException("Variacao com SKU: " + variacao.getSku() + ", passe as opçoẽs que compõem a variação.");
				}
				
				opcaoService.validaOpcoes(variacao.getOpcoes());
				quantidade += variacao.getQuantidade();
			}
			obj.setQuantidade(quantidade);
			produto = repo.save(obj);
			for (ProdutoVariacao variacao : obj.getVariacoes()) {
				variacao.setProduto(produto);
			}
			
			produtoVariacaoService.saveAll(obj.getVariacoes());

		} else {			
			produto = repo.save(obj);
		}
			
		return produto;
	}
	
	private void validaOpcoes(List<Opcao> opcoes) {
		for (Opcao opcao : opcoes) {
			if (!opcaoService.opcaoIsCadastrada(opcao)) {
				throw new DataIntegrityException("Opção ID: " + opcao.getId() + " não é uma opção válida.");
			}
		}
	}
	
	private List<Categoria> addArvoreCategoriaNoProduto(Categoria categoria) {
		List<Categoria> listaCategorias = new ArrayList<>();
		listaCategorias.add(categoria.getCategoriaPai());
		if (categoria.getCategoriaPai().getCategoriaPai() != null) {
			listaCategorias.addAll(addArvoreCategoriaNoProduto(categoria.getCategoriaPai()));
		}
		
		return listaCategorias;
	}
	
	public void atualizaQuantidade(Produto obj, Integer qtdDecremento) {
		Produto produto = find(obj.getId());
		Integer novaQuantidade = produto.getQuantidade() - qtdDecremento;
		
		if (novaQuantidade < 0) throw new DataIntegrityException("Variação ID: " + produto.getId() + 
				" a quantidade passada para o estoque deve ser ser no mínimo 0: " + obj.getQuantidade());
		
		produto.setQuantidade(novaQuantidade);
		repo.save(produto);
	}

}
