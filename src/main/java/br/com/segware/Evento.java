package br.com.segware;

import java.text.ParseException;
import java.util.Date;

public class Evento {
	
	private String coseq;
	private String codCliente;
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
