package br.com.segware;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AnalisadorRelatorio implements IAnalisadorRelatorio {

	private Stream<Evento> eventos;
	private static final Long TEMPO_DESARME_PERMITIDO_MINUTOS = 5L;

	public AnalisadorRelatorio(List<Evento> eventos) {
		this.eventos = eventos.stream();
	}

	public AnalisadorRelatorio(Stream<Evento> eventos) {
		this.eventos = eventos;
	}

	@Override
	public Map<String, Integer> getTotalEventosCliente() {
		Map<String, Integer> totalEventosCliente = eventos
				.collect(Collectors.groupingBy(Evento::getCodigoCliente, Collectors.summingInt(e -> 1)));
		return totalEventosCliente;
	}

	@Override
	public Map<String, Long> getTempoMedioAtendimentoAtendente() {
		Map<String, Long> tempoMedioAtendimentoAtendentes = new HashMap<String, Long>();
		Map<String, List<Evento>> eventosPorAtendente = eventos
				.collect(Collectors.groupingBy(Evento::getCodigoAtendente));
		eventosPorAtendente.keySet().forEach(atendente -> {
			List<Evento> eventosDaAtendente = eventosPorAtendente.get(atendente);
			Long quantidadeAtendimentos = (long) eventosDaAtendente.size();
			Long duracaoTotalAtendimentos = eventosDaAtendente.stream()
					.mapToLong(a -> getDuracaoAtendimentoEmSegundos(a)).sum();
			Long tempoMedioAtendimento = duracaoTotalAtendimentos / quantidadeAtendimentos;
			tempoMedioAtendimentoAtendentes.put(atendente, tempoMedioAtendimento);
		});
		return tempoMedioAtendimentoAtendentes;
	}

	private Long getDuracaoAtendimentoEmSegundos(Evento evento) {
		return ChronoUnit.SECONDS.between(evento.getDataInicio(), evento.getDataFim());
	}

	@Override
	public List<Tipo> getTiposOrdenadosNumerosEventosDecrescente() {
		Map<Tipo, Integer> tipos = eventos
				.collect(Collectors.groupingBy(Evento::getTipo, Collectors.summingInt(e -> 1)));
		List<Tipo> tiposOrdenados = tipos.keySet().stream()
				.sorted(Comparator.comparingInt(tipo -> tipos.get(tipo)).reversed()).collect(Collectors.toList());
		return tiposOrdenados;
	}

	@Override
	public List<Integer> getCodigoSequencialEventosDesarmeAposAlarme() {
		List<Integer> codigosSequenciais = new ArrayList<Integer>();
		Map<String, List<Evento>> eventosPorCliente = eventos.collect(Collectors.groupingBy(Evento::getCodigoCliente));
		eventosPorCliente.keySet().forEach(cliente -> {
			List<Integer> codigos = getCodigosSequenciasDesarmeAposAlarmePorCliente(eventosPorCliente.get(cliente));
			codigosSequenciais.addAll(codigos);
		});
		return codigosSequenciais;
	}

	private List<Integer> getCodigosSequenciasDesarmeAposAlarmePorCliente(List<Evento> eventosDoCliente) {
		List<Integer> codigosSequenciais = new ArrayList<Integer>();
		Evento ultimoEventoAlarme = null;
		for (Iterator<Evento> iterator = eventosDoCliente.iterator(); iterator.hasNext();) {
			Evento evento = iterator.next();
			Tipo tipoEvento = evento.getTipo();
			if (tipoEvento.equals(Tipo.ALARME)) {
				ultimoEventoAlarme = evento;
			} else if (tipoEvento.equals(Tipo.DESARME) && ultimoEventoAlarme != null) {
				if (desarmeDentroDoTempoPermitido(ultimoEventoAlarme, evento))
					codigosSequenciais.add(evento.getCodigoSequencial());
				ultimoEventoAlarme = null;
			}
		}
		return codigosSequenciais;
	}

	private boolean desarmeDentroDoTempoPermitido(Evento eventoAlarme, Evento eventoDesarme) {
		long tempoDesarmeEfetuado = ChronoUnit.MINUTES.between(eventoAlarme.getDataInicio(),
				eventoDesarme.getDataFim());
		return TEMPO_DESARME_PERMITIDO_MINUTOS.compareTo(tempoDesarmeEfetuado) > 0;
	}

}
