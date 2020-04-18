package com.joaovictor.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joaovictor.cursomc.domain.ProdutoVariacao;
import com.joaovictor.cursomc.repositories.ProdutoVariacaoRepository;

@Service	
public class ProdutoVariacaoService {
	
	@Autowired
	private ProdutoVariacaoRepository produtoVariacaoRepository;
	
	public List<ProdutoVariacao> findAll() {
		return produtoVariacaoRepository.findAll();
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
		return produtoVariacaoRepository.saveAll(variacoes);
	}
}
