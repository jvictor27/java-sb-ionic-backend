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
import com.joaovictor.cursomc.domain.ProdutoVariacao;

public class ProdutoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String nome;
	
	private Double preco;
	
	private List<CategoriaSimplesDTO> categorias = new ArrayList<>();
	
	private List<ProdutoVariacaoSimplesDTO> variacoes = new ArrayList<>();
	
	public ProdutoDTO() {
		
	}
	
	public ProdutoDTO(Produto obj) {
		id = obj.getId();
		nome = obj.getNome();
		preco = obj.getPreco();
		
		for (Categoria categoria : obj.getCategorias()) {
			CategoriaSimplesDTO cat = new CategoriaSimplesDTO(categoria);
			categorias.add(cat);
		}
	
		for (ProdutoVariacao variacao : obj.getVariacoes()) {
			variacoes.add(new ProdutoVariacaoSimplesDTO(variacao));
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

	public List<ProdutoVariacaoSimplesDTO> getVariacoes() {
		return variacoes;
	}

	public void setVariacoes(List<ProdutoVariacaoSimplesDTO> variacoes) {
		this.variacoes = variacoes;
	}
}
