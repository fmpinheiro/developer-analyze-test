package br.com.segware;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class LeitorArquivo {

	private final static int CODIGO_SEQUENCIAL = 0;
	private final static int CODIGO_CLIENTE = 1;
	private final static int CODIGO_EVENTO = 2;
	private final static int TIPO = 3;
	private final static int DATA_INICIO = 4;
	private final static int DATA_FIM = 5;
	private final static int CODIGO_ATENDENTE = 6;

	private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public List<Evento> lerEventosDoArquivo(Path pathArquivo) throws IOException {
		List<Evento> eventos = new ArrayList<Evento>();

		try (Stream<String> linhas = Files.lines(pathArquivo)) {
			linhas.forEach(linha -> {
				Evento evento = lerEvento(linha);
				eventos.add(evento);
			});
			return eventos;
		}
	}

	private Evento lerEvento(String linhaArquivo) {
		String[] atributosEventos = linhaArquivo.split(",");

		Evento evento = new Evento();
		evento.setCodigoSequencial(Integer.valueOf(atributosEventos[CODIGO_SEQUENCIAL]));
		evento.setCodigoCliente(atributosEventos[CODIGO_CLIENTE]);
		evento.setCodigo(atributosEventos[CODIGO_EVENTO]);
		evento.setTipo(Tipo.valueOf(atributosEventos[TIPO]));
		evento.setDataInicio(parseData(atributosEventos[DATA_INICIO]));
		evento.setDataFim(parseData(atributosEventos[DATA_FIM]));
		evento.setCodigoAtendente(atributosEventos[CODIGO_ATENDENTE]);
		return evento;
	}

	private LocalDateTime parseData(String data) {
		return LocalDateTime.parse(data, DATE_TIME_FORMATTER);
	}

}
