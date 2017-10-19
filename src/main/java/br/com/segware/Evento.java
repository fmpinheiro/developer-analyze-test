package br.com.segware;

import java.text.ParseException;
import java.util.Date;

public class Evento {
	
	private String coseq;
	private String codCliente;
	public String getCoseq() {
		return coseq;
	}

	public void setCoseq(String coseq) {
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

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public String getCodAtendente() {
		return codAtendente;
	}

	public void setCodAtendente(String codAtendente) {
		this.codAtendente = codAtendente;
	}

	private Tipo tipoEvento;
	private Date dataInicio;
	private Date dataFim;
	private String codAtendente;

	public Evento(String[] linha) throws ParseException {
		this.coseq = linha[0];
		this.codCliente = linha[1];
		this.tipoEvento = Tipo.valueOf(linha[3]);
		this.dataInicio = Utils.toDate(linha[4]);
		this.dataFim = Utils.toDate(linha[5]);
		this.codAtendente = linha[6];
	}	
}
