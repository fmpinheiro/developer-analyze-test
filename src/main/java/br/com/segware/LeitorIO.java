package br.com.segware;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Faz a leitura do arquivo relatorio.csv no projeto
 * @author Danilo
 *
 */
public class LeitorIO {
	
	
	
	private Relatorio relatorio = new Relatorio();
	
	/**
	 * faz a leitura do arquivo e popula o objeto relatorio
	 */
	private void lerArquivo() {

		try {
			
			FileReader arq = new FileReader(new File("src/test/java/br/com/segware/relatorio.csv"));
			BufferedReader lerArq = new BufferedReader(arq);

			String linha = null;
			do {
				
				linha = lerArq.readLine(); 
				if(linha != null) {
					relatorio.adiciona(linha.split(","));
				}
				
			}while (linha != null);

			arq.close();
		} catch (IOException e) {
			System.err.printf("Erro na leitura do arquivo: %s.\n", e.getMessage());
		}

		//System.out.println("Leitura arquivo completa.");
	}
	
	
	
	public Relatorio getRelatorio() {
		lerArquivo();
		return relatorio;
	}



	public static void main(String[] args) {
		LeitorIO leitor = new LeitorIO();
		
		leitor.lerArquivo();
		
	}

}
