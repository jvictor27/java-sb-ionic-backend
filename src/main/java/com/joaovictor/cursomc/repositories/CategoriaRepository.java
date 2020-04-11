package com.joaovictor.cursomc.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.joaovictor.cursomc.domain.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
	
	@Transactional(readOnly=true)
//	@Query("SELECT DISTINCT obj FROM Categoria obj WHERE obj.nivelCategoria = :nivel")
	List<Categoria> findByNivel(@Param("nivel") Integer nivel);
	
	@Transactional(readOnly=true)
	@Query("SELECT DISTINCT obj FROM Categoria obj WHERE obj.nivel = :nivel")
	Page<Categoria> search(@Param("nivel") Integer nivel, Pageable pageRequest);
	
	@Transactional(readOnly=true)
	@Query("SELECT obj FROM Categoria obj WHERE obj.categoriaPai.id = :categoriaPaiId")
	List<Categoria> findByCategoriaPaiId(@Param("categoriaPaiId") Integer categoriaPaiId);
}
