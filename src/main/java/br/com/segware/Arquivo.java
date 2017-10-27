package br.com.segware;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class Arquivo {

	String caminhoArq = "src/test/java/br/com/segware/relatorio.csv";
	
	public ArrayList<Evento> lerArquivo() {
		String linha;
		String[] parsedLinha;
		ArrayList<Evento> linhas = new ArrayList<>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(caminhoArq));
			while(br.ready()) {
				linha = br.readLine();
				parsedLinha = linha.split(",");
				linhas.add(new Evento(parsedLinha[0], parsedLinha[1], parsedLinha[2], parsedLinha[3], parsedLinha[4], parsedLinha[5], parsedLinha[6]));
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
		        try {
		        	br.close();
		        }
		        catch (Exception e) {
		        	e.printStackTrace();
		        }
		    }
		}
		return linhas;
	}
	
}
