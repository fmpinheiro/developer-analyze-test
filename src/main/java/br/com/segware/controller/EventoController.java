package br.com.segware.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.segware.model.Evento;
import br.com.segware.service.EventoService;
import br.com.segware.service.Service;
import br.com.segware.util.Reader;

public class EventoController implements Controller {

	private final Service<Evento> service = new EventoService();
	
	private Map<String, List<String[]>> fileContent;
	
	@Override
	public void loadCsv(final String fileName, final String separador) {
		fileContent = Reader.readCsv(fileName, separador);
		final List<String[]> records = fileContent.get("content");
		
		final Set<Evento> cities = new HashSet<>();
		for (String[] column : records) {
			final Evento city = new Evento();
			city.setIbgeId(Long.parseLong(column[0]));
			city.setUf(column[1]);
			city.setName(column[2]);
			city.setCapital(Boolean.valueOf(column[3]));
			city.setLongitude(Double.valueOf(column[4]));
			city.setLatitude(Double.valueOf(column[5]));
			city.setNoAccents(column[6]);
			city.setAlternativeNames(column[7]);
			city.setMicroregion(column[8]);
			city.setMesoregion(column[9]);
			cities.add(city);
		}
		
		//1,0001,E130,ALARME,2014-06-25 12:00:00,2014-06-25 12:05:32,AT01̣̣
		service.insertAll(cities);
	}

}
