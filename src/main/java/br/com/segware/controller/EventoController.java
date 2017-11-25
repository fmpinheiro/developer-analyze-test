package br.com.segware.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.segware.Tipo;
import br.com.segware.model.Evento;
import br.com.segware.service.EventoService;
import br.com.segware.service.Service;
import br.com.segware.util.Constants;
import br.com.segware.util.Message;
import br.com.segware.util.Reader;

public class EventoController implements Controller<Evento> {

	private final Service<Evento> service = new EventoService();
	private Map<String, List<String[]>> fileContent;
	private String csvFileName;
	
	public EventoController(final String csvFileName) {
		this.csvFileName = csvFileName;
	}
	
	@Override
	public void loadCsv() {
		fileContent = Reader.readCsv(this.csvFileName, Constants.CSV_SEPARATOR);
		final List<String[]> records = fileContent.get(Constants.CONTENT);
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		final Set<Evento> eventos = new HashSet<>();
		for (String[] column : records) {
			final Evento evento = new Evento();
			evento.setCodigoSequencial(Long.parseLong(column[0]));
			evento.setCodigoCliente(column[1]);
			evento.setCodigoEvento(column[2]);
			evento.setTipo(Tipo.valueOf(column[3]));
			evento.setCodigoAtendente(column[6]);
			
			try {
				evento.setDataInicio(sdf.parse(column[4]));
				evento.setDataFim(sdf.parse(column[5]));
			} catch (ParseException e) {
				Message.printError(e.getMessage());
			}
			eventos.add(evento);
		}
		service.insertAll(eventos);
	}

	@Override
	public Set<Evento> findAll() {
		return service.findAll();
	}
	
}
