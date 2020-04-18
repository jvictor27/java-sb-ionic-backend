package com.joaovictor.cursomc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.joaovictor.cursomc.domain.Opcao;

@Repository
public interface OpcaoRepository extends JpaRepository<Opcao, Integer> {

	@Transactional(readOnly=true)
	@Query("SELECT obj FROM Opcao obj WHERE obj.tipoOpcao.id = :tipoOpcaoId ORDER BY obj.nome")
	public List<Opcao> findOpcoes(@Param("tipoOpcaoId") Integer tipoOpcaoId);
}
