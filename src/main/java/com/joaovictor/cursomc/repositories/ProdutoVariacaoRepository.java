package com.joaovictor.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.joaovictor.cursomc.domain.ProdutoVariacao;

@Repository
public interface ProdutoVariacaoRepository extends JpaRepository<ProdutoVariacao, Integer> {

}
