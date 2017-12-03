package br.com.segware;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Evento {
	private Integer codSequencial;
	private String codCliente;
	private String codEvento;
	private Tipo tipoEvento;
	private LocalDateTime dataIni;
	private LocalDateTime dataFim;
	private String codAtendente;
	private final static DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private final static int COD_SEQ = 0;
	private final static int COD_CLI = 1;
	private final static int COD_EVENTO = 2;
	private final static int TIPO_EVENTO = 3;
	private final static int DT_INICIO = 4;
	private final static int DT_FIM = 5;
	private final static int COD_ATD = 6;
	
	public Evento(String[] lineInput) {
		this.codSequencial = Integer.valueOf(lineInput[COD_SEQ]);
		this.codCliente = lineInput[COD_CLI];
		this.codEvento = lineInput[COD_EVENTO];
		this.tipoEvento = Tipo.valueOf(lineInput[TIPO_EVENTO]);
		this.dataIni = parseData(lineInput[DT_INICIO]);
		this.dataFim = parseData(lineInput[DT_FIM]);
		this.codAtendente = lineInput[COD_ATD];
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

	public LocalDateTime getDataIni() {
		return dataIni;
	}

	public LocalDateTime getDataFim() {
		return dataFim;
	}

	public String getCodAtd() {
		return codAtendente;
	}
	public long getDiffDatas(){
		return ChronoUnit.SECONDS.between(dataIni, dataFim);
	} 
	
	private LocalDateTime parseData(String data) {
			return LocalDateTime.parse(data, formato);
			}
}
