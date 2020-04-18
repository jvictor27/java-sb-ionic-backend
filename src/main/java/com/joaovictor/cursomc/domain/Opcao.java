package com.joaovictor.cursomc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Opcao implements Serializable {
	private static final long serialVerionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	
	@ManyToOne
	@JoinColumn(name="tipoopcao_id")
	private TipoOpcao tipoOpcao;
	
	@ManyToMany(mappedBy = "opcoes")
	private List<ProdutoVariacao> variacoes = new ArrayList<>();
	
	public Opcao() {
		
	}
	
	public Opcao(Integer id, String nome, TipoOpcao tipoOpcao) {
		super();
		this.id = id;
		this.nome = nome;
		this.tipoOpcao = tipoOpcao;
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

	public List<ProdutoVariacao> getVariacoes() {
		return variacoes;
	}

	public void setVariacoes(List<ProdutoVariacao> variacoes) {
		this.variacoes = variacoes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Opcao other = (Opcao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
