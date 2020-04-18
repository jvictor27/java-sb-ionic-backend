package com.joaovictor.cursomc.dto;

import java.io.Serializable;

import com.joaovictor.cursomc.domain.Opcao;
import com.joaovictor.cursomc.domain.TipoOpcao;

public class OpcaoSimplesDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;
	private TipoOpcao tipoOpcao;

	public OpcaoSimplesDTO() {
	}

	public OpcaoSimplesDTO(Opcao obj) {
		id = obj.getId();
		nome = obj.getNome();
		tipoOpcao = obj.getTipoOpcao();
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

	public TipoOpcao getTipoOpcao() {
		return tipoOpcao;
	}

	public void setTipoOpcao(TipoOpcao tipoOpcao) {
		this.tipoOpcao = tipoOpcao;
	}
}