package br.com.segware;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import br.com.segware.controller.Controller;
import br.com.segware.model.Evento;
import br.com.segware.model.TotalTipoEvento;
import br.com.segware.util.DateUtil;

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

		//XXX - Refatorar
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
		final Set<Evento> eventos = controller.findAll();
		final Map<String, Long> quantidadeAtentimentosAtendente = new HashMap<>();
		final Map<String, Long> tempoGastoAtendentes = new HashMap<>();
		
//		XXX - Refatorar
		for (Evento evento : eventos) {
			final String codigoAtendente = evento.getCodigoAtendente();
			final Long tempoGastoAtendente = DateUtil.calculateDateDiff(evento.getDataInicio(), 
					evento.getDataFim(), TimeUnit.SECONDS);
			
			if (!quantidadeAtentimentosAtendente.containsKey(codigoAtendente)) {
				quantidadeAtentimentosAtendente.put(codigoAtendente, 1L);
				tempoGastoAtendentes.put(codigoAtendente, tempoGastoAtendente);
				continue;
			}
			final Long quantidadeAtendimentosAtendente = quantidadeAtentimentosAtendente.get(codigoAtendente);
			quantidadeAtentimentosAtendente.put(codigoAtendente, quantidadeAtendimentosAtendente + 1);
			final Long tempoAtendimentoAtendente = tempoGastoAtendentes.get(codigoAtendente);
			tempoGastoAtendentes.put(codigoAtendente, tempoAtendimentoAtendente + tempoGastoAtendente);
		}
		
		final Map<String, Long> tempoMedioAtendentes = new HashMap<>();
		for (Entry<String, Long> entry : quantidadeAtentimentosAtendente.entrySet()) {
		    final String codigoAtendente = entry.getKey();
		    final Long tempoGastoAtendente = tempoGastoAtendentes.get(codigoAtendente);
		    final Long quantidadeAtendimentos = quantidadeAtentimentosAtendente.get(codigoAtendente);
		    
		    final Long mediaAtendimento = tempoGastoAtendente/quantidadeAtendimentos;
		    if (!tempoMedioAtendentes.containsKey(codigoAtendente)) {
		    	tempoMedioAtendentes.put(codigoAtendente, mediaAtendimento);
		    	continue;
		    }
		    final Long mediaAtendimentoAtendente = tempoMedioAtendentes.get(codigoAtendente);
		    tempoMedioAtendentes.put(codigoAtendente, mediaAtendimentoAtendente + mediaAtendimento);
		}
		
		return tempoMedioAtendentes;
	}

	@Override
	public List<Tipo> getTiposOrdenadosNumerosEventosDecrescente() {
		final Set<Evento> eventos = controller.findAll();
		final Map<Tipo, Long> quantidadeEventosTipo = new HashMap<>();
		
//		XXX - Refatorar
		for (Evento evento : eventos) {
			final Tipo tipoEvento = evento.getTipo();
			if (!quantidadeEventosTipo.containsKey(tipoEvento)) {
				quantidadeEventosTipo.put(tipoEvento, 1L);
				continue;
			}
			final Long quantidadeAtendimentosAtendente = quantidadeEventosTipo.get(tipoEvento);
			quantidadeEventosTipo.put(tipoEvento, quantidadeAtendimentosAtendente + 1);
		}
		
		final List<TotalTipoEvento> totaisTipoEvento = new ArrayList<>();
		
		for (Entry<Tipo, Long> entry : quantidadeEventosTipo.entrySet()) {
			final TotalTipoEvento totalTipoEvento = new TotalTipoEvento();
			totalTipoEvento.setTipo(entry.getKey());
			totalTipoEvento.setQuantidade(entry.getValue());
			totaisTipoEvento.add(totalTipoEvento);
		}
		
		Collections.sort(totaisTipoEvento, new Comparator<TotalTipoEvento>() {
			@Override
			public int compare(final TotalTipoEvento o1, final TotalTipoEvento o2) {
				return o2.getQuantidade().compareTo(o1.getQuantidade());
			}
		});

		final List<Tipo> tiposEvento = new ArrayList<>();
		for (TotalTipoEvento tipoEvento : totaisTipoEvento) {
			tiposEvento.add(tipoEvento.getTipo());
		}
		return tiposEvento;
		
	}

	@Override
	public List<Integer> getCodigoSequencialEventosDesarmeAposAlarme() {
		// TODO Auto-generated method stub
		return null;
	}

}
