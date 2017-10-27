package br.com.segware;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AnalisadorRelatorioImpl implements IAnalisadorRelatorio {

	@Override
	public Map<String, Integer> getTotalEventosCliente() {
		Map<String, Integer> totalEventosCliente = new HashMap<String, Integer>();
		Arquivo relatorio = new Arquivo();
		
		ArrayList<Evento> listaEventos = relatorio.lerArquivo();		
		totalEventosCliente = listaEventos.stream().collect(Collectors.groupingBy(Evento::getCodCliente, Collectors.summingInt(x -> 1)));
		
		return totalEventosCliente;
	}

	@Override
	public Map<String, Long> getTempoMedioAtendimentoAtendente() {
		Map<String, Long> tempoMedioAtendimentoAtendente = new HashMap<String, Long>();
		Map<String, Double> calculoTempoMedioAtendimentoAtendente = new HashMap<String, Double>();
		Arquivo relatorio = new Arquivo();
		
		ArrayList<Evento> listaEventos = relatorio.lerArquivo();
		calculoTempoMedioAtendimentoAtendente = listaEventos.stream().collect(Collectors.groupingBy(Evento::getCodAtendente, Collectors.averagingLong(Evento::getTempoAtendimento)));
		
		// Converte Map<String, Double> para Map<String, Long>	
		for (Map.Entry<String, Double> tempoMedio : calculoTempoMedioAtendimentoAtendente.entrySet()) {
			if(tempoMedio.getValue() instanceof Double){
				tempoMedioAtendimentoAtendente.put(tempoMedio.getKey(), Math.round(tempoMedio.getValue()));
		    }
		}		
		
		return tempoMedioAtendimentoAtendente;
	}

	@Override
	public List<Tipo> getTiposOrdenadosNumerosEventosDecrescente() {
		List<Tipo> tiposOrdenadosNumerosEventosDecrescente = new ArrayList<Tipo>();
		Arquivo relatorio = new Arquivo();
		
		ArrayList<Evento> listaEventos = relatorio.lerArquivo();		
		EnumMap<Tipo, Long> mapTipoEventos = listaEventos.stream().collect(Collectors.groupingBy(Evento::getTipoEvento, ()->new EnumMap<>(Tipo.class), Collectors.counting()));
		
		// Ordena a List<EnumMap.Entry<Tipo, Long>> com base nos values do EnumMap<Tipo, Long> 
		List<EnumMap.Entry<Tipo, Long>> listMap = new LinkedList<EnumMap.Entry<Tipo, Long>>(mapTipoEventos.entrySet());
		Collections.sort(listMap, new Comparator<EnumMap.Entry<Tipo, Long>>() {
            public int compare(EnumMap.Entry<Tipo, Long> val2,
                               EnumMap.Entry<Tipo, Long> val1) {
                return (val1.getValue()).compareTo(val2.getValue());
            }
        });
		
		// Insere os já ordenados valores na List<Tipo>
		for (EnumMap.Entry<Tipo, Long> numEventos: listMap) {
            tiposOrdenadosNumerosEventosDecrescente.add(numEventos.getKey());
        }
		
		return tiposOrdenadosNumerosEventosDecrescente;
	}

	@Override
	public List<Integer> getCodigoSequencialEventosDesarmeAposAlarme() {
		List<Integer> codigoSequencialEventosDesarmeAposAlarme = new ArrayList<Integer>();
		List<Long> codigoSequencialEventosDesarmeAposAlarme2 = new ArrayList<Long>();
		Arquivo relatorio = new Arquivo();
		
		ArrayList<Evento> listaEventos = relatorio.lerArquivo();		
		for (Evento eventoDesarme: listaEventos) {
			if (eventoDesarme.getTipoEvento().equals(Tipo.DESARME)) {
				for (Evento eventoAlarme: listaEventos) {
					
					// Verifica se o evento de ALARME ocorreu antes do de DESARME, se foi no mesmo cliente e se a diferença entre os eventos é de menos de 300000 Milisecs (5 Minutos)
					if (eventoAlarme.getTipoEvento().equals(Tipo.ALARME) && 
							eventoDesarme.getCodCliente().equals(eventoAlarme.getCodCliente()) &&
								eventoDesarme.getDatInicio().getTime() > eventoAlarme.getDatInicio().getTime() && 
									eventoDesarme.getDatInicio().getTime() - eventoAlarme.getDatInicio().getTime() <= 300000) {
						codigoSequencialEventosDesarmeAposAlarme2.add(eventoDesarme.getDatInicio().getTime() - eventoAlarme.getDatInicio().getTime());
						codigoSequencialEventosDesarmeAposAlarme.add(eventoDesarme.getCodSequesncial());
					}
					
				}
			}
		}

		return codigoSequencialEventosDesarmeAposAlarme;
	}

}
