package com.joaovictor.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joaovictor.cursomc.domain.ProdutoVariacao;
import com.joaovictor.cursomc.repositories.ProdutoVariacaoRepository;
import com.joaovictor.cursomc.services.exceptions.DataIntegrityException;
import com.joaovictor.cursomc.services.exceptions.ObjectNotFoundException;

@Service	
public class ProdutoVariacaoService {
	
	@Autowired
	private ProdutoVariacaoRepository repo;
	
	public List<ProdutoVariacao> findAll() {
		return repo.findAll();
	}
	
	public ProdutoVariacao find(Integer id) {
		Optional<ProdutoVariacao> obj = repo.findById(id);
		obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + ProdutoVariacao.class.getName()));
		
		ProdutoVariacao variacao = obj.get();
		return variacao;
	}
	
	public boolean produtoVariacaoCadastrada(ProdutoVariacao produtoVariacao) {
		List<ProdutoVariacao> variacoes = findAll();
		boolean isCadastrada = false;
		
		if (variacoes.contains(produtoVariacao)) {
			isCadastrada = true;
		}
		
		return isCadastrada;
	}
	
	public List<ProdutoVariacao> saveAll(List<ProdutoVariacao> variacoes) {
		return repo.saveAll(variacoes);
	}
	
	public void atualizaQuantidade(ProdutoVariacao obj, Integer qtdDecremento) {
		ProdutoVariacao produtoVariacao = find(obj.getId());
		Integer novaQuantidade = produtoVariacao.getQuantidade() - qtdDecremento;
		
		if (novaQuantidade < 0) throw new DataIntegrityException("Variação SKU: " + produtoVariacao.getSku() + 
				" a quantidade passada para o estoque deve ser ser no mínimo 0: " + obj.getQuantidade());
		
		produtoVariacao.setQuantidade(novaQuantidade);
		repo.save(produtoVariacao);
	}
}
