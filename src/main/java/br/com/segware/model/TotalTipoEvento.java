package br.com.segware.model;

import br.com.segware.enums.Tipo;

public class TotalTipoEvento {

	private Tipo tipo;
	private Long quantidade;

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(final Tipo tipo) {
		this.tipo = tipo;
	}

	public Long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(final Long quantidade) {
		this.quantidade = quantidade;
	}

}
