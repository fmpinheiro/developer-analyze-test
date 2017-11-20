package br.com.segware;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LeitorIO {

	private final String caminho = "src/test/java/br/com/segware/relatorio.csv";
	private BufferedReader br;
	private String linha;
	private List<String[]> arqLido;

	public List<String[]> lerArquivo() throws IOException {
		br = null;
		linha = "";
		arqLido = new ArrayList<>();
		br = new BufferedReader(new FileReader(caminho));
		String[] linhaArq = null;
		while ((linha = br.readLine()) != null) {
			linhaArq = linha.split(",");
			arqLido.add(linhaArq);
		}

		return arqLido;
	}

	public List<String[]> getArqLido() {
		return arqLido;
	}

}