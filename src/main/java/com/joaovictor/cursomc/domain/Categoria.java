package com.joaovictor.cursomc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.joaovictor.cursomc.domain.enums.NivelCategoria;

@Entity
public class Categoria implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	
	@Column(nullable=false)
	private Integer nivel;
	
//	@JsonIgnore
	@ManyToMany(mappedBy="categorias")
	private List<Produto> produtos = new ArrayList<>();
	
//	@JsonIgnore
//	@JsonIgnoreProperties({"categoriasFilhas", "nome", "produtos", "categoriaPai", "categoriasFilhas"})
	@ManyToOne
    @JoinColumn(name = "categoria_pai_id", nullable=true)  
	private Categoria categoriaPai;
	
//	@JsonIgnore
	@OneToMany(mappedBy="categoriaPai")
	private List<Categoria> categoriasFilhas = new ArrayList<>();
	
	public Categoria() {
	}

	public Categoria(Integer id, String nome, NivelCategoria nivelCategoria) {
		super();
		this.id = id;
		this.nome = nome;
		this.nivel = (nivelCategoria == null) ? null : nivelCategoria.getCod();
	}
	
	public Categoria(Integer id, String nome, NivelCategoria nivelCategoria, Categoria categoriaPai) {
		super();
		this.id = (id == null) ? null : id;
		this.nome = nome;
		this.nivel = (nivelCategoria == null) ? null : nivelCategoria.getCod();
		this.categoriaPai = (categoriaPai == null) ? null : categoriaPai;
	}

	@JsonGetter
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@JsonGetter
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
	
	public Categoria getCategoriaPai() {
		return categoriaPai;
	}

	public void setCategoriaPai(Categoria categoriaPai) {
		this.categoriaPai = categoriaPai;
	}
	
	public List<Categoria> getCategoriasFilhas() {
		return categoriasFilhas;
	}

	public void setCategoriasFilhas(List<Categoria> categoriasFilhas) {
		this.categoriasFilhas = categoriasFilhas;
	}
	
	public Integer getNivel() {
		return nivel;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
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
		Categoria other = (Categoria) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
