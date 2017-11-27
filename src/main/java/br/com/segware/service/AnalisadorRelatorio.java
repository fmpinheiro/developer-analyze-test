package br.com.segware.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import br.com.segware.controller.Controller;
import br.com.segware.enums.Tipo;
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
		final List<Evento> eventos = controller.findAll();
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
		final Map<String, Long> quantidadeAtentimentosAtendente = new HashMap<>();
		final Map<String, Long> tempoGastoAtendentes = new HashMap<>();
		final Map<String, Long> tempoMedioAtendentes = new HashMap<>();

		totalizarAtendimentosAtendente(quantidadeAtentimentosAtendente, tempoGastoAtendentes);
		totalizarTempoMedioAtendentes(quantidadeAtentimentosAtendente, tempoGastoAtendentes, tempoMedioAtendentes);
		
		return tempoMedioAtendentes;
	}

	private void totalizarTempoMedioAtendentes(final Map<String, Long> quantidadeAtentimentosAtendente,
			final Map<String, Long> tempoGastoAtendentes, final Map<String, Long> tempoMedioAtendentes) {
		
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
	}

	private void totalizarAtendimentosAtendente(final Map<String, Long> quantidadeAtentimentosAtendente,
			final Map<String, Long> tempoGastoAtendentes) {
		final List<Evento> eventos = controller.findAll();
		
		for (Evento evento : eventos) {
			final String codigoAtendente = evento.getCodigoAtendente();
			final Long tempoGastoAtendente = DateUtil.calculateDateDiff(evento.getDataInicio(), 
					evento.getDataFim(), TimeUnit.SECONDS);
			
			if (!quantidadeAtentimentosAtendente.containsKey(codigoAtendente)) {
				quantidadeAtentimentosAtendente.put(codigoAtendente, 1L);
				tempoGastoAtendentes.put(codigoAtendente, tempoGastoAtendente);
				continue;
			}
			somarAgrupado(quantidadeAtentimentosAtendente, codigoAtendente, 1L);
			somarAgrupado(tempoGastoAtendentes, codigoAtendente, tempoGastoAtendente);
		}
	}

	private void somarAgrupado(final Map<String, Long> atendimentos, final String codigoAgrupador, final Long valorSomar) {
		final Long valorAtual = atendimentos.get(codigoAgrupador);
		atendimentos.put(codigoAgrupador, valorAtual + valorSomar);
	}

	@Override
	public List<Tipo> getTiposOrdenadosNumerosEventosDecrescente() {
		final Map<Tipo, Long> quantidadeEventosTipo = new HashMap<>();
		final List<TotalTipoEvento> totaisTipoEvento = new ArrayList<>();

		agruparEventosTipo(quantidadeEventosTipo);
		totalizarTiposEvento(quantidadeEventosTipo, totaisTipoEvento);
		
		return getTiposEventosOrdenados(totaisTipoEvento);
	}

	private List<Tipo> getTiposEventosOrdenados(final List<TotalTipoEvento> totaisTipoEvento) {
		ordernarTotaisTipoEventoDecrescente(totaisTipoEvento);
		final List<Tipo> tiposEvento = new ArrayList<>();
		for (TotalTipoEvento tipoEvento : totaisTipoEvento) {
			tiposEvento.add(tipoEvento.getTipo());
		}
		return tiposEvento;
	}

	private void totalizarTiposEvento(final Map<Tipo, Long> quantidadeEventosTipo,
			final List<TotalTipoEvento> totaisTipoEvento) {
		
		for (Entry<Tipo, Long> entry : quantidadeEventosTipo.entrySet()) {
			final TotalTipoEvento totalTipoEvento = new TotalTipoEvento();
			totalTipoEvento.setTipo(entry.getKey());
			totalTipoEvento.setQuantidade(entry.getValue());
			totaisTipoEvento.add(totalTipoEvento);
		}
	}

	private void ordernarTotaisTipoEventoDecrescente(final List<TotalTipoEvento> totaisTipoEvento) {
		Collections.sort(totaisTipoEvento, new Comparator<TotalTipoEvento>() {
			@Override
			public int compare(final TotalTipoEvento o1, final TotalTipoEvento o2) {
				return o2.getQuantidade().compareTo(o1.getQuantidade());
			}
		});
	}
	
	private void ordernarEventosCrescente(final List<Evento> eventos) {
		Collections.sort(eventos, new Comparator<Evento>() {
			@Override
			public int compare(final Evento o1, final Evento o2) {
				return o1.getCodigoSequencial().compareTo(o2.getCodigoSequencial());
			}
		});
	}

	private void agruparEventosTipo(final Map<Tipo, Long> quantidadeEventosTipo) {
		final List<Evento> eventos = getCopy(controller.findAll());
		for (Evento evento : eventos) {
			final Tipo tipoEvento = evento.getTipo();
			if (!quantidadeEventosTipo.containsKey(tipoEvento)) {
				quantidadeEventosTipo.put(tipoEvento, 1L);
				continue;
			}
			final Long quantidadeAtendimentosAtendente = quantidadeEventosTipo.get(tipoEvento);
			quantidadeEventosTipo.put(tipoEvento, quantidadeAtendimentosAtendente + 1);
		}
	}
	
	private List<Evento> getCopy(final List<Evento> original) {
		return new ArrayList<>(original);
	}

	@Override
	public List<Integer> getCodigoSequencialEventosDesarmeAposAlarme() {
		final List<Evento> eventosCliente = getCopy(controller.findAll());
		final List<Integer> codigosSequenciaisEventos = new ArrayList<>();
		final Map<String, Evento> ultimoAlarmeClientes = new HashMap<>();
		final int limiteMinutosDesarme = 5;

		ordernarEventosCrescente(eventosCliente);
		
		for (int i = 1; i < eventosCliente.size(); i++) {
			final Evento eventoAnterior = eventosCliente.get(i-1);
			final Evento eventoCorrente = eventosCliente.get(i);
			
			if (eventoAnterior.getTipo().equals(Tipo.ALARME)) {
				ultimoAlarmeClientes.put(eventoAnterior.getCodigoCliente(), eventoAnterior);
			}
			
			if (ultimoAlarmeClientes.containsKey(eventoCorrente.getCodigoCliente())) {
				final Evento ultimoEventoCliente = ultimoAlarmeClientes.get(eventoCorrente.getCodigoCliente());
				
				if (Tipo.DESARME.equals(eventoCorrente.getTipo())) {
					final Long minutosDesarme = DateUtil.calculateDateDiff(ultimoEventoCliente.getDataInicio(), 
							eventoCorrente.getDataInicio(), TimeUnit.MINUTES);
					
					if (minutosDesarme > 0 && minutosDesarme <= limiteMinutosDesarme) {
						codigosSequenciaisEventos.add(eventoCorrente.getCodigoSequencial().intValue());
					}
				}
			}
			
		}
		return codigosSequenciaisEventos;
	}

}
