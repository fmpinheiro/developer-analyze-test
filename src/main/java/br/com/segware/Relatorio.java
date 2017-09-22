package br.com.segware;

import java.util.ArrayList;
import java.util.List;
 

/**
 * 
 * @author Danilo
 *
 */
public class Relatorio {
	
	public static final int CODIGO_SEQUENCIAL = 0;
	public static final int CODIGO_CLIENTE = 1;
	public static final int CODIGO_EVENTO = 2;
	public static final int TIPO_EVENTO = 3;
	public static final int DATA_INICIO = 4;
	public static final int DATA_FIM = 5;
	public static final int CODIGO_ATENDENTE = 6;
	
	private List<String[]> relatorio = new ArrayList<String[]>();

	public void adiciona(String[] linha) {
		relatorio.add(linha);
	}
	
	public List<String[]> getReltorio(){
		return relatorio;
	}
	
	

}
