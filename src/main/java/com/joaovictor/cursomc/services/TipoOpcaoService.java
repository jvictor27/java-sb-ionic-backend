package com.joaovictor.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joaovictor.cursomc.domain.TipoOpcao;
import com.joaovictor.cursomc.repositories.TipoOpcaoRepository;

@Service
public class TipoOpcaoService {

	@Autowired
	private TipoOpcaoRepository repo;

	public List<TipoOpcao> findAll() {
		return repo.findAllByOrderByNome();
	}
}