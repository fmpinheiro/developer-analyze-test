package br.com.segware;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Evento {

	private Integer codSequencial;
	private String codCliente;
	private String codEvento;
	private Tipo tipoEvento;
	private DateTime dataIni;
	private DateTime dataFim;
	private String codAtendent;
	private DateTimeFormatter frm = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

	public Evento(String[] linhaArq) {
		this.codSequencial = Integer.valueOf(linhaArq[0]);
		this.codCliente = linhaArq[1];
		this.codEvento = linhaArq[2];
		this.tipoEvento = Tipo.valueOf(linhaArq[3]);
		this.dataIni = frm.parseDateTime(linhaArq[4]);
		this.dataFim = frm.parseDateTime(linhaArq[5]);
		this.codAtendent = linhaArq[6];
	}

	public Integer getCodSequencial() {
		return codSequencial;
	}

	public String getCodCliente() {
		return codCliente;
	}

	public String getCodEvento() {
		return codEvento;
	}

	public Tipo getTipoEvento() {
		return tipoEvento;
	}

	public DateTime getDataIni() {
		return dataIni;
	}

	public DateTime getDataFim() {
		return dataFim;
	}

	public String getCodAtendent() {
		return codAtendent;
	}

}