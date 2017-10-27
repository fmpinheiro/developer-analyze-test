package br.com.segware;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Evento {
	
	private Integer codSequencial;
	private String codCliente;
	private String codEvento;
	private String codAtendente;
	private Tipo tipoEvento;
	private Date datInicio;
	private Date datFim;
	private long tempoAtendimento;
	
	public Evento(String codSequesncial, String codCliente, String codEvento, String tipoEvento,String strDatInicio, String strDatFim, String codAtendente) throws ParseException {
		super();
		
		this.codSequencial = Integer.parseInt(codSequesncial);
		this.codCliente = codCliente;
		this.codEvento = codEvento;
		this.codAtendente = codAtendente;
		this.tipoEvento = Tipo.valueOf(tipoEvento);		
		this.datInicio = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strDatInicio);
		this.datFim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strDatFim);		
		this.setTempoAtendimento((datFim.getTime() - datInicio.getTime())/1000);		
	}
	
	public Integer getCodSequesncial() {
		return codSequencial;
	}
	
	public void setCodSequesncial(Integer codSequesncial) {
		this.codSequencial = codSequesncial;
	}
	
	public String getCodCliente() {
		return codCliente;
	}
	
	public void setCodCliente(String codCliente) {
		this.codCliente = codCliente;
	}
	
	public String getCodEvento() {
		return codEvento;
	}
	
	public void setCodEvento(String codEvento) {
		this.codEvento = codEvento;
	}
	
	public Tipo getTipoEvento() {
		return tipoEvento;
	}
	
	public void setTipoEvento(Tipo tipoEvento) {
		this.tipoEvento = tipoEvento;
	}
	
	public Date getDatInicio() {
		return datInicio;
	}
	
	public void setDatInicio(Date datInicio) {
		this.datInicio = datInicio;
	}
	
	public Date getDatFim() {
		return datFim;
	}
	
	public void setDatFim(Date datFim) {
		this.datFim = datFim;
	}
	
	public String getCodAtendente() {
		return codAtendente;
	}
	
	public void setCodAtendente(String codAtendente) {
		this.codAtendente = codAtendente;
	}
	
	public long getTempoAtendimento() {
		return tempoAtendimento;
	}
	
	public void setTempoAtendimento(long mediaTempoAtendimento) {
		this.tempoAtendimento = mediaTempoAtendimento;
	}
}
