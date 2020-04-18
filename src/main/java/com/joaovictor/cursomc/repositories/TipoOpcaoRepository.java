package com.joaovictor.cursomc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.joaovictor.cursomc.domain.TipoOpcao;

@Repository
public interface TipoOpcaoRepository extends JpaRepository<TipoOpcao, Integer> {

	@Transactional(readOnly=true)
	public List<TipoOpcao> findAllByOrderByNome();
}
