package br.com.segware.dao.impl;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.segware.Tipo;
import br.com.segware.constantes.FileNames;
import br.com.segware.dao.EventoDAO;
import br.com.segware.model.Evento;

public final class ArquivoEventoDAO implements EventoDAO{
	
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	public ArquivoEventoDAO() {
	}
	
	@SuppressWarnings("resource")
	private Scanner scanearArquivo(FileNames fileName) {
		try {
			return new Scanner(new FileReader(fileName.getFile())).useDelimiter(",|\\r\\n");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Não foi possível ler o arquivo.", e);
		}
	}
	
	public List<Evento> getEventos() {
		List<Evento> eventos = new ArrayList<>();
		Scanner arquivo = scanearArquivo(FileNames.RELATORIO);
		while (arquivo.hasNext()) {
			Integer codigo = Integer.valueOf(arquivo.next().trim());
			String codigoCliente = arquivo.next().trim();
			String codigoEvento = arquivo.next().trim();
			Tipo tipoEvento = Tipo.valueOf(arquivo.next().trim());
			LocalDateTime dataInicio = LocalDateTime.parse(arquivo.next().trim(), DATE_TIME_FORMATTER);
			LocalDateTime dataFim = LocalDateTime.parse(arquivo.next().trim(), DATE_TIME_FORMATTER);
			String codigoAtendente = arquivo.next().trim();
			eventos.add(new Evento(codigo, codigoCliente, codigoEvento, tipoEvento, dataInicio, dataFim, codigoAtendente));
		}
		return eventos;
	}
}
