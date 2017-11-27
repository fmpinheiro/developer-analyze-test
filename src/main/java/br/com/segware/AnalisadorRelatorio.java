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
	private Set<Evento> eventos;

	public AnalisadorRelatorio(final Controller<Evento> controller) {
		this.controller = controller;
		init();
	}
	
	private void init() {
		this.controller.loadCsv();
		this.eventos = controller.findAll();
	}

	@Override
	public Map<String, Integer> getTotalEventosCliente() {
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
		ordernarDecrescente(totaisTipoEvento);
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

	private void ordernarDecrescente(final List<TotalTipoEvento> totaisTipoEvento) {
		Collections.sort(totaisTipoEvento, new Comparator<TotalTipoEvento>() {
			@Override
			public int compare(final TotalTipoEvento o1, final TotalTipoEvento o2) {
				return o2.getQuantidade().compareTo(o1.getQuantidade());
			}
		});
	}

	private void agruparEventosTipo(final Map<Tipo, Long> quantidadeEventosTipo) {
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

	@Override
	public List<Integer> getCodigoSequencialEventosDesarmeAposAlarme() {
//		final Set<Evento> eventos = controller.findAll();
//		final Map<String, List<Evento>> eventosCliente = new HashMap<>();
//
//		List<Evento> eventosAgrupar = null;
//		
//		for (Evento evento : eventos) {
//			final String codigoCliente = evento.getCodigoCliente();
//			if (!eventosCliente.containsKey(codigoCliente)) {
//				eventosAgrupar = new ArrayList<>();
//				eventosCliente.put(codigoCliente, eventosAgrupar);
//			}
//			eventosCliente.get(codigoCliente).add(evento);
//		}
//		
//		List<Integer> codigosSequenciais = new ArrayList<>();
//		for (Entry<String, List<Evento>> entry : eventosCliente.entrySet()) {
//			final List<Evento> sequenciaEventosCliente = entry.getValue();
//			
//			Evento ultimoEvento = sequenciaEventosCliente.get(0);
//			Tipo ultimoTipo = ultimoEvento.getTipo();
//			String ultimoCliente = ultimoEvento.getCodigoCliente();
//			Long ultimoIntervaloMinutos = DateUtil.calculateDateDiff(ultimoEvento.getDataInicio(), 
//					ultimoEvento.getDataInicio(), TimeUnit.MINUTES);
//			
//			for (Evento evento : sequenciaEventosCliente) {
//				if (ultimoTipo.equals(Tipo.ALARME) && evento.getTipo().equals(Tipo.DESARME)) {
//					codigosSequenciais.add(evento.getCodigoSequencial().intValue());
//					ultimoIntervaloMinutos = DateUtil.calculateDateDiff(evento.getDataInicio(), ultimoEvento.getDataInicio(), TimeUnit.MINUTES);
//					ultimoEvento = evento;
//					ultimoTipo = evento.getTipo();
//					ultimoCliente = evento.getCodigoCliente();
//				}
//			}
//		}
//		
		return null;
	}

}
