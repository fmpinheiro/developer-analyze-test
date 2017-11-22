package br.com.segware;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.*;

import org.apache.commons.lang3.Validate;
import org.joda.time.Duration;

import java.io.IOException;


public class AnalisadorRelatorioImpl implements IAnalisadorRelatorio {	

	/**
	 * Implementação Interface IAnalisadorRelatorio<br/> Total de eventos
	 *
	 */
	RepositoryEventosCSV repositorio = new RepositoryEventosCSV();	
	
	@Override
	public Map<String, Integer> getTotalEventosCliente() {		
		try {
			 Map<String, Long> mapOld = repositorio.getEventosAll().stream()
					 .collect(Collectors.groupingBy(Evento::getCodigoCliente, Collectors.counting() ));
			 
			 Map<String, Integer> mapNew = mapOld.entrySet().stream().collect(
						Collectors.toMap( Map.Entry::getKey ,  e -> e.getValue().intValue()));
				
			 return mapNew;
		} catch (IOException e) {			
			e.printStackTrace();
			return null;
		}		
	}

	@Override
	public Map<String, Long> getTempoMedioAtendimentoAtendente() {
			try {
				Map<String, Double> mapOld = ( repositorio.getEventosAll().stream()
						.collect(Collectors.groupingBy( Evento::getCodigoAntendente, 
								Collectors.averagingLong(Evento::getDiferentInSeconds) 
										) ) );
								
				//Necessary Parse does Map<String, Double> to Map<String, Long>				
				Map<String, Long> mapNew = mapOld.entrySet().stream().collect(
						Collectors.toMap( Map.Entry::getKey ,  e -> e.getValue().longValue()));		
				
				return mapNew;
				
			} catch (IOException e) {				
				// TODO Auto-generated catch block
				e.printStackTrace();
				return  null;
			}
					
					
		
	}
	
	
	public Map<String, Double> getTempoMedioAtendimentoAtendenter() {
		try {
			return repositorio.getEventosAll().stream()
					.collect(Collectors.groupingBy( Evento::getCodigoCliente, 
							Collectors.averagingLong(Evento::getDiferentInSeconds) 
									) );
		} catch (IOException e) {			
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Tipo> getTiposOrdenadosNumerosEventosDecrescente() {
		try {
			List<Tipo> listTipo = repositorio.getEventosAll().stream()
					 .collect(Collectors.groupingBy(Evento::getTipoEvento, Collectors.counting()))
					 .entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
					 .map(Map.Entry::getKey)
					 .collect(Collectors.toList());
				
			 return listTipo;
		} catch (IOException e) {			
			e.printStackTrace();
			return null;
		}	
	}

	

    /**
     * Retorna o codigo sequencial de um evento de desarme que tenha ocorrido apos alarme.<br/>
     * Importante notar que este tipo de evento so pode ser considerado quando o desarme ocorrer em ate 5 minutos apos o alarme.<br/>
     * Caso tenha excedido este periodo, nao devera ser reportado.<br/>
     * O tempo a ser considerado e sempre com base na data/hora inicial dos eventos comparados.
     *
     * @return Lista de codigos sequenciais de eventos com desarme apos o alarme.
     */
	
	@Override
	public List<Integer> getCodigoSequencialEventosDesarmeAposAlarme() {
		List<Integer> sequencialList = new ArrayList<Integer>();
		try {
			List<Evento> collectDesarme = repositorio.getEventosAll().stream()
					.filter(map -> map.getTipoEvento() == Tipo.DESARME)
		            .collect(Collectors.toList());
			
			List<Evento> collectAlarme = repositorio.getEventosAll().stream()
					.filter(map -> map.getTipoEvento() == Tipo.ALARME)
		            .collect(Collectors.toList());
			
			for(Evento desarmeList : collectDesarme){
				for(Evento alarmeList : collectAlarme){										
					if( desarmeList.getCodigoCliente().equals( alarmeList.getCodigoCliente() )){
					Duration duration = new Duration(alarmeList.getDataInicio(), desarmeList.getDataInicio());
					System.out.println("duration: " + duration.getStandardMinutes());
						if(duration.getStandardMinutes() > 0 && duration.getStandardMinutes() <= 5){								
						sequencialList.add( Integer.parseInt(desarmeList.getCodigoSequencial()) );
						}
						break;		
					}
					
				}
			}
			
			 System.out.println("Collect : " + sequencialList);
			 return sequencialList;
		} catch (IOException e) {			
			e.printStackTrace();
			return null;
		}	
	}	
	
	
	

}
