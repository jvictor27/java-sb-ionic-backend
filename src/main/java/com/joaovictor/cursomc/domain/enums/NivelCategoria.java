package com.joaovictor.cursomc.domain.enums;

public enum NivelCategoria {
	
	PRIMEIRONIVEL(1, "Primeiro nivel"),
	SEGUNDONIVEL(2, "Segundo nivel"),
	TERCEIRONIVEL(3, "Terceiro nivel"),
	QUARTONIVEL(4, "Quarto nivel");
	
	private Integer cod;
	private String descricao;
	
	private NivelCategoria(Integer cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public static NivelCategoria toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}
		
		for (NivelCategoria x : NivelCategoria.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: " + cod);
	}
}
