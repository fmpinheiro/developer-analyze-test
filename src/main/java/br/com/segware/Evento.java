package br.com.segware;

import org.joda.time.DateTime;
import org.joda.time.Seconds; 

public class Evento {
	
	private String codigoSequencial;
	private String codigoCliente;
	private String codigoEvento;
	private Tipo tipoEvento;
	private DateTime dataInicio;
	private DateTime dataFim;
	private String codigoAntendente;	
		
	
	public Evento() {	}
	
	
	public Evento(String codigoSequencial, String codigoCliente, String codigoEvento, Tipo tipoEvento,
			DateTime dataInicio, DateTime dataFim, String codigoAntendente) {
		super();
		this.codigoSequencial = codigoSequencial;
		this.codigoCliente = codigoCliente;
		this.codigoEvento = codigoEvento;
		this.tipoEvento = tipoEvento;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.codigoAntendente = codigoAntendente;
	}

	public Long getDiferentInSeconds(){
		long seconds = Seconds.secondsBetween(getDataInicio(), getDataFim()).getSeconds();
		return seconds;
	}

	public String getCodigoSequencial() {
		return codigoSequencial;
	}


	public void setCodigoSequencial(String codigoSequencial) {
		this.codigoSequencial = codigoSequencial;
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


	public DateTime getDataInicio() {
		return dataInicio;
	}


	public void setDataInicio(DateTime dataInicio) {
		this.dataInicio = dataInicio;
	}


	public DateTime getDataFim() {
		return dataFim;
	}


	public void setDataFim(DateTime dataFim) {
		this.dataFim = dataFim;
	}


	public String getCodigoAntendente() {
		return codigoAntendente;
	}


	public void setCodigoAntendente(String codigoAntendente) {
		this.codigoAntendente = codigoAntendente;
	}


	@Override
	public String toString() {
		return "Evento [codigoSequencial=" + codigoSequencial + ", codigoCliente=" + codigoCliente + ", codigoEvento="
				+ codigoEvento + ", tipoEvento=" + tipoEvento + ", dataInicio=" + dataInicio + ", dataFim=" + dataFim
				+ ", codigoAntendente=" + codigoAntendente + "]";
	}


	
	
	

}
