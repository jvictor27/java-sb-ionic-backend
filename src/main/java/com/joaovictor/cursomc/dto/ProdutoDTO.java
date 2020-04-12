package com.joaovictor.cursomc.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.joaovictor.cursomc.domain.Categoria;
import com.joaovictor.cursomc.domain.Produto;

public class ProdutoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String nome;
	
	private Double preco;
	
	private List<CategoriaSimplesDTO> categorias = new ArrayList<>();
	
	public ProdutoDTO() {
		
	}
	
	public ProdutoDTO(Produto obj) {
		id = obj.getId();
		nome = obj.getNome();
		preco = obj.getPreco();
		categorias = null;
		if (obj.getCategorias() != null && obj.getCategorias().size() > 0) {
			for (Categoria categoria : obj.getCategorias()) {
				categorias.add(new CategoriaSimplesDTO(categoria));
			}
		}
	
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

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}
	
	public List<CategoriaSimplesDTO> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<CategoriaSimplesDTO> categorias) {
		this.categorias = categorias;
	}
}
