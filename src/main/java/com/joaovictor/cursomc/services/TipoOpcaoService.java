package com.joaovictor.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joaovictor.cursomc.domain.Categoria;
import com.joaovictor.cursomc.domain.TipoOpcao;
import com.joaovictor.cursomc.repositories.TipoOpcaoRepository;
import com.joaovictor.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class TipoOpcaoService {

	@Autowired
	private TipoOpcaoRepository repo;

	public List<TipoOpcao> findAll() {
		return repo.findAllByOrderByNome();
	}
	
	public TipoOpcao find(Integer id) {
		Optional<TipoOpcao> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + TipoOpcao.class.getName()));
	}
}