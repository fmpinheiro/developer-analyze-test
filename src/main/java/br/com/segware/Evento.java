package br.com.segware;

import java.time.LocalDateTime;

public class Evento {

	private int codigoSequencial;
	private String codigoCliente;
	private String codigo;
	private Tipo tipo;
	private LocalDateTime dataInicio;
	private LocalDateTime dataFim;
	private String codigoAtendente;

	public int getCodigoSequencial() {
		return codigoSequencial;
	}

	public void setCodigoSequencial(int codigoSequencial) {
		this.codigoSequencial = codigoSequencial;
	}

	public String getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(String codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public LocalDateTime getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDateTime dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDateTime getDataFim() {
		return dataFim;
	}

	public void setDataFim(LocalDateTime dataFim) {
		this.dataFim = dataFim;
	}

	public String getCodigoAtendente() {
		return codigoAtendente;
	}

	public void setCodigoAtendente(String codigoAtendente) {
		this.codigoAtendente = codigoAtendente;
	}

}
