package com.joaovictor.cursomc.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.joaovictor.cursomc.domain.Categoria;

public class CategoriaSimplesDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	@NotEmpty(message="Preenchimento obrigatório")
	@Length(min=5, max=80, message="O tamanho deve ser entre 5 e 80 caracteres")
	private String nome;
	
	private Integer nivel;
	
	private CategoriaPaiDTO categoriaPai;
	
	public CategoriaSimplesDTO() {
		
	}
	
	public CategoriaSimplesDTO(Categoria obj) {
		id = obj.getId();
		nome = obj.getNome();
		categoriaPai = (obj.getCategoriaPai() == null) ? null : new CategoriaPaiDTO(obj.getCategoriaPai());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public CategoriaPaiDTO getCategoriaPai() {
		return categoriaPai;
	}

	public void setCategoriaPaiDTO(CategoriaPaiDTO categoriaPai) {
		this.categoriaPai = categoriaPai;
	}
	
}
