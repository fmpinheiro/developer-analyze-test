package br.com.segware;

import java.text.ParseException;
import java.util.Date;

import org.joda.time.DateTime;

public class Evento {
	
	private int coseq;
	private String codCliente;
	private Tipo tipoEvento;
	private DateTime dataInicio;
	private DateTime dataFim;
	private String codAtendente;

	public Evento(String[] linha) throws ParseException {
		this.coseq = Integer.parseInt((linha[0]));
		this.codCliente = linha[1];
		this.tipoEvento = Tipo.valueOf(linha[3]);
		this.dataInicio = Utils.toDate(linha[4]);
		this.dataFim = Utils.toDate(linha[5]);
		this.codAtendente = linha[6];
	}	
	
	public int getCoseq() {
		return coseq;
	}

	public void setCoseq(int coseq) {
		this.coseq = coseq;
	}

	public String getCodCliente() {
		return codCliente;
	}

	public void setCodCliente(String codCliente) {
		this.codCliente = codCliente;
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

	public String getCodAtendente() {
		return codAtendente;
	}

	public void setCodAtendente(String codAtendente) {
		this.codAtendente = codAtendente;
	}


}
