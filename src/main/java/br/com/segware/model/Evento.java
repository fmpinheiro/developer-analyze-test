package br.com.segware.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import br.com.segware.Tipo;

public class Evento {

	private Integer codigo;
	private String codigoCliente;
	private String codigoEvento;
	private Tipo tipoEvento;
	private LocalDateTime dataInicio;
	private LocalDateTime dataFim;
	private String codigoAtendente;
	
	public Evento() {
	}

	public Evento(Integer codigo, String codigoCliente, String codigoEvento, Tipo tipoEvento, LocalDateTime dataInicio, LocalDateTime dataFim, String codigoAtendente) {
		this.codigo = codigo;
		this.codigoCliente = codigoCliente;
		this.codigoEvento = codigoEvento;
		this.tipoEvento = tipoEvento;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.codigoAtendente = codigoAtendente;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(String codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	public String getCodigoEvento() {
		return codigoEvento;
	}

	public void setCodigoEvento(String codigoEvento) {
		this.codigoEvento = codigoEvento;
	}

	public Tipo getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(Tipo tipoEvento) {
		this.tipoEvento = tipoEvento;
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
	
	public Long getTempoAtendimento(){
		return dataInicio.until(dataFim, ChronoUnit.SECONDS);
	}
}
