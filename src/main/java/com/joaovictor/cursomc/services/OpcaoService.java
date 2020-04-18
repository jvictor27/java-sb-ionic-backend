package com.joaovictor.cursomc.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joaovictor.cursomc.domain.Opcao;
import com.joaovictor.cursomc.domain.ProdutoVariacao;
import com.joaovictor.cursomc.repositories.OpcaoRepository;
import com.joaovictor.cursomc.services.exceptions.DataIntegrityException;

@Service
public class OpcaoService {

	@Autowired
	private OpcaoRepository repo;

	public List<Opcao> findByTipoOpcao(Integer tipoOpcaoId) {
		return repo.findOpcoes(tipoOpcaoId);
	}
	
	public List<Opcao> findAll() {
		return repo.findAll();
	}
	
	public boolean opcaoIsCadastrada(Opcao opcao) {
		List<Opcao> opcoes = findAll();
		boolean isCadastrada = false;
		
		if (opcoes.contains(opcao)) {
			isCadastrada = true;
		}
		
		return isCadastrada;
	}
	
	public void validaOpcoes(List<Opcao> opcoes) {
		for (Opcao opcao : opcoes) {
			if (!opcaoIsCadastrada(opcao)) {
				throw new DataIntegrityException("Opção ID: " + opcao.getId() + " não é uma opção válida.");
			}
		}
	}
}
