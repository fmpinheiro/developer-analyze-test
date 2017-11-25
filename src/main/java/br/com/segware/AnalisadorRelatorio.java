package br.com.segware;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.segware.controller.Controller;
import br.com.segware.model.Evento;

public class AnalisadorRelatorio implements IAnalisadorRelatorio {

	private Controller<Evento> controller;
	
	public AnalisadorRelatorio(final Controller<Evento> controller) {
		this.controller = controller;
		this.controller.loadCsv();
	}
	
	@Override
	public Map<String, Integer> getTotalEventosCliente() {
		final Set<Evento> eventos = controller.findAll();
		final Map<String, Integer> numeroEventosCliente = new HashMap<>();
		
		for (Evento evento : eventos) {
			final String codigoCliente = evento.getCodigoCliente();
			if (!numeroEventosCliente.containsKey(codigoCliente)) {
				numeroEventosCliente.put(codigoCliente, 1);
				continue;
			}
			final Integer totalEventosCliente = numeroEventosCliente.get(codigoCliente) + 1;
			numeroEventosCliente.put(codigoCliente, totalEventosCliente);
		}
		return numeroEventosCliente;
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
