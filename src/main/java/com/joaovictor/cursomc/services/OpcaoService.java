package com.joaovictor.cursomc.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joaovictor.cursomc.domain.Opcao;
import com.joaovictor.cursomc.repositories.OpcaoRepository;

@Service
public class OpcaoService {

	@Autowired
	private OpcaoRepository repo;

	public List<Opcao> findByTipoOpcao(Integer tipoOpcaoId) {
		return repo.findOpcoes(tipoOpcaoId);
	}
}
