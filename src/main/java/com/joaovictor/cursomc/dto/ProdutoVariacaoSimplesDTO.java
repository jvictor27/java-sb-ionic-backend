package com.joaovictor.cursomc.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.joaovictor.cursomc.domain.Opcao;
import com.joaovictor.cursomc.domain.ProdutoVariacao;

public class ProdutoVariacaoSimplesDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String sku;
	private Integer quantidade;
	
	private List<OpcaoSimplesDTO> opcoes = new ArrayList<>();

	public ProdutoVariacaoSimplesDTO(ProdutoVariacao obj) {
		super();
		this.id = obj.getId();
		this.sku = obj.getSku();
		this.quantidade = obj.getQuantidade();
		for (Opcao opcao : obj.getOpcoes()) {			
			opcoes.add(new OpcaoSimplesDTO(opcao));
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public List<OpcaoSimplesDTO> getOpcoes() {
		return opcoes;
	}

	public void setOpcoes(List<OpcaoSimplesDTO> opcoes) {
		this.opcoes = opcoes;
	}
}
