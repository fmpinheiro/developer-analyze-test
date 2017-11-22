package br.com.segware;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Iterator;

import com.opencsv.CSVReader;

public class RepositoryEventosCSV {
	
	private Evento evento;
	private List<Evento> listEvento;
	private CSVReader reader;
	private DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
			
	
	private CSVReader obterReaderFileCSV() throws FileNotFoundException{
		return new CSVReader(new FileReader("src/test/java/br/com/segware/relatorio.csv"), ',');
	}
	
	private void fecharReaderFileCSV() throws IOException{
			this.reader.close();
	}

	
	public List<Evento> getEventosAll() throws IOException{
		this.reader = obterReaderFileCSV();		
		this.listEvento = new ArrayList<Evento>();		
		List<String[]> records = reader.readAll();

		Iterator<String[]> iterator = records.iterator();

		while (iterator.hasNext()) {
			String[] record = iterator.next();
			evento = new Evento();
			evento.setCodigoSequencial(record[0]);
			evento.setCodigoCliente(record[1]);
			evento.setCodigoEvento(record[2]);
			evento.setTipoEvento(Tipo.valueOf(record[3]));
			evento.setDataInicio( formatter.parseDateTime(record[4]) );
			evento.setDataFim( formatter.parseDateTime(record[5]) );
			evento.setCodigoAntendente(record[6]);
			listEvento.add(evento);
		}
		
		fecharReaderFileCSV();
		
		return listEvento;		
	}
	
	

}
