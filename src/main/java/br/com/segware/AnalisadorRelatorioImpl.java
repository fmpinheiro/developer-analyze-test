package br.com.segware;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
/**
 * 
 * @author Danilo
 *
 */
public class AnalisadorRelatorioImpl implements IAnalisadorRelatorio {
	
	private Relatorio relatorio;
	
	public AnalisadorRelatorioImpl(Relatorio relatorio) {
		this.relatorio = relatorio;
	}

	@Override
	public Map<String, Integer> getTotalEventosCliente() {

		Map<String, Integer> result = new HashMap<String, Integer>();
		
		for(String[] evento: relatorio.getReltorio()) {
			String chave = evento[Relatorio.CODIGO_CLIENTE];
			
			Integer valor = result.get(chave); 
			valor = valor != null ? Integer.sum(valor.intValue(), 1) : 1;
			
			result.put(chave, valor);
		}
		return result;
	}

	@Override
	public Map<String, Long> getTempoMedioAtendimentoAtendente() {
		Map<String, Long> result = new HashMap<String, Long>();
		
		
		Long valor = null;
		for(String[] evento: relatorio.getReltorio()) {
			String chave = evento[Relatorio.CODIGO_ATENDENTE];
			
			valor = result.get(chave); 
			
			Date dt1 = getDateFromString(evento[Relatorio.DATA_INICIO]);
			Date dt2 = getDateFromString(evento[Relatorio.DATA_FIM]);
			
			long d1 = dt1 == null ? 0 : dt1.getTime();
			long d2 = dt1 == null ? 0 : dt2.getTime();
			
			if(valor == null) {
				valor = new Long(0);
			}
			valor += (d2-d1);
		
			result.put(chave, valor);
			valor = null;
		}
		
		for(Map.Entry<String, Long> entrada : result.entrySet()) {
			result.put(entrada.getKey(), entrada.getValue()/10000);
		}
		
		return result;
	}

	@Override
	public List<Tipo> getTiposOrdenadosNumerosEventosDecrescente() {
		
		Map<Tipo, Integer> mapAux = new HashMap<Tipo, Integer>();
		
		int count = 0;
		
		for (String[] evento : relatorio.getReltorio()) {

			String chave = evento[Relatorio.TIPO_EVENTO];
			Tipo tipoChave = getTipo(chave);
			count = mapAux.containsKey(tipoChave) ? mapAux.get(tipoChave) : 0;
			mapAux.put(tipoChave, ++count);
			count = 0;

		}

		List<Map.Entry<Tipo, Integer>> list = new LinkedList<Map.Entry<Tipo, Integer>>(mapAux.entrySet());

		Collections.sort(list, new Comparator<Map.Entry<Tipo, Integer>>() {
			public int compare(Map.Entry<Tipo, Integer> o1, Map.Entry<Tipo, Integer> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});

		Map<Tipo, Integer> mapAuxOrdenado = new LinkedHashMap<Tipo, Integer>();
		for (Map.Entry<Tipo, Integer> entry : list) {
			mapAuxOrdenado.put(entry.getKey(), entry.getValue());
		}

		return new ArrayList<>(mapAuxOrdenado.keySet());
	}

	@Override
	public List<Integer> getCodigoSequencialEventosDesarmeAposAlarme() {
		
		List<Integer> listaCodigoSequencial = new ArrayList<Integer>();
		
		int start = 0;
		// Laço para percorrer a lista de eventos
		for (String[] evento : relatorio.getReltorio()) {
			
			Tipo tipoAnt = getTipo(evento[Relatorio.TIPO_EVENTO]);
			int codCliAnt = Integer.parseInt(evento[Relatorio.CODIGO_CLIENTE]);
			Date dtInicioAnt = getDateFromString(evento[Relatorio.DATA_INICIO]);

			start++;
			
			if(!tipoAnt.equals(Tipo.ALARME)) {
				continue;
			}
			
			// Laco para percorrer a mesma lista a partir do evento seguinte
			for (int i = start; i < relatorio.getReltorio().size(); i++) {
				
				String[] evtPosterior = relatorio.getReltorio().get(i);
				
				Tipo tipoAtual = getTipo(evtPosterior[Relatorio.TIPO_EVENTO]);
				int codCliAtual = Integer.parseInt(evtPosterior[Relatorio.CODIGO_CLIENTE]);
				Date dtInicioAtual = getDateFromString(evtPosterior[Relatorio.DATA_INICIO]);
				
				// Se o tipo for desarme e o codigo do cliente for igual e a dif entre o inicio for menor que 5 min
				// adiciona a lista de resultaos
				if(codCliAnt == codCliAtual && tipoAtual.equals(Tipo.DESARME) && difDataSegundos(dtInicioAnt, dtInicioAtual) <= 300) {
					listaCodigoSequencial.add(new Integer(evtPosterior[Relatorio.CODIGO_SEQUENCIAL]));
					break;
				}
			}
		}
		return listaCodigoSequencial;
	}
	
	
	/**
	 *  Devolve a enum tipo a partir de uma string
	 *  
	 * @param String strTipo
	 * @return Tipo.java
	 */
	private Tipo getTipo(String strTipo) {
		
		Tipo tipoChave = null; 
		
		switch (strTipo) {
		case "ALARME":
			tipoChave = Tipo.ALARME;
			break;
		case "ARME":
			tipoChave = Tipo.ARME;
			break;
		case "DESARME":
			tipoChave = Tipo.DESARME;
			break;
		case "TESTE":
			tipoChave = Tipo.TESTE;
			break;

		default:
			break;
		}
		return tipoChave;
	}
	
	/**
	 * Retorna diferenca entre dt1 e dt2 em segundos
	 * 
	 * @param Date dt1
	 * @param Date dt2
	 * @return Long
	 */
	private Long difDataSegundos(Date dt1, Date dt2) {
		long seconds;
		long diferenca = dt1.getTime() - dt2.getTime();
		seconds = diferenca / 1000;
		return new Long(seconds);
	}
	
	
	/**
	 * Retorna uma data a partir de uma String com o formato yyyy-MM-dd HH:mm:ss
	 *  
	 * @param String strDate
	 * @return Date
	 */
	private Date getDateFromString(String strDate) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date retorno = null;
		try {
			retorno = fmt.parse(strDate);
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		
		return retorno;
	}

		
}
