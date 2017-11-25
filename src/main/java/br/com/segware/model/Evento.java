package br.com.segware.model;

import java.util.Date;

import br.com.segware.Tipo;

public class Evento {

	private Long codigoSequencial;
	private String codigoCliente;
	private String codigoEvento;
	private Tipo tipo;
	private Date dataInicio;
	private Date dataFim;
	private String codigoAtendente;

	public Long getCodigoSequencial() {
		return codigoSequencial;
	}

	public void setCodigoSequencial(final Long codigoSequencial) {
		this.codigoSequencial = codigoSequencial;
	}

	public String getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(final String codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	public String getCodigoEvento() {
		return codigoEvento;
	}

	public void setCodigoEvento(final String codigoEvento) {
		this.codigoEvento = codigoEvento;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(final Tipo tipo) {
		this.tipo = tipo;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(final Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(final Date dataFim) {
		this.dataFim = dataFim;
	}

	public String getCodigoAtendente() {
		return codigoAtendente;
	}

	public void setCodigoAtendente(final String codigoAtendente) {
		this.codigoAtendente = codigoAtendente;
	}

}
