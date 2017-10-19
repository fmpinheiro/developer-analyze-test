package br.com.segware;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;

public class RelatorioDAO {
	private CSVReader reader;
	
	public RelatorioDAO(String fileName) throws FileNotFoundException{
		this.reader = new CSVReader(new FileReader(fileName),',');
	}

	public List<Evento> readFile() throws IOException, ParseException {
		List<String[]> linhas = this.reader.readAll();
		List<Evento> eventos = this.convert(linhas);
		return eventos;
	}

	private List<Evento> convert(List<String[]> linhas) throws ParseException {
		List<Evento> eventos = new ArrayList();
		for(String[] linha :linhas){
			eventos.add(new Evento(linha));
		}
		return eventos;
	}
}
