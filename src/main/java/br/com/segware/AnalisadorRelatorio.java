package br.com.segware;

import java.util.List;
import java.util.Map;

public class AnalisadorRelatorio implements IAnalisadorRelatorio {

	private Controller controller;
	
	public AnalisadorRelatorio(final Controller controller) {
		
	}
	
	@Override
	public Map<String, Integer> getTotalEventosCliente() {
		return null;
	}

	@Override
	public Map<String, Long> getTempoMedioAtendimentoAtendente() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tipo> getTiposOrdenadosNumerosEventosDecrescente() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> getCodigoSequencialEventosDesarmeAposAlarme() {
		// TODO Auto-generated method stub
		return null;
	}

}
