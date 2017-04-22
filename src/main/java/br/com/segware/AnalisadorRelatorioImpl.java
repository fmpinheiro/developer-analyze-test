package br.com.segware;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.com.segware.dao.EventoDAO;
import br.com.segware.dao.impl.ArquivoEventoDAO;
import br.com.segware.model.Evento;

public class AnalisadorRelatorioImpl implements IAnalisadorRelatorio {
	
	private EventoDAO eventoDAO;
	private List<Evento> eventos;
	
	public AnalisadorRelatorioImpl() {
		eventoDAO = new ArquivoEventoDAO();
		eventos = eventoDAO.getEventos();
	}

	@Override
	public Map<String, Integer> getTotalEventosCliente() {
		Map<String, Integer> totalEventosCliente = new HashMap<>();
		eventos
		.stream()
		.map(Evento::getCodigoCliente)
		.forEach(codigoCliente -> {
			Long total = eventos.stream().filter(evento -> codigoCliente.equals(evento.getCodigoCliente())).count();
			totalEventosCliente.put(codigoCliente, total.intValue());
		});
		return totalEventosCliente;
	}

	@Override
	public Map<String, Long> getTempoMedioAtendimentoAtendente() {
		Map<String, Long> tempoMedioAtendimento = new HashMap<>();
		eventos
		.stream()
		.map(Evento::getCodigoAtendente)
		.distinct()
		.forEach(codigoAtendente -> {
			Supplier<Stream<Evento>> supplierEventosFiltrados = () -> eventos
					.stream()
					.filter(evento -> codigoAtendente.equals(evento.getCodigoAtendente()));
			Long totalAtendimentos = supplierEventosFiltrados.get().count();
			Long totalSegundosAtendimentos = supplierEventosFiltrados.get()
					.map(Evento::getTempoAtendimento)
					.reduce((long) 0, (tempoAtendimento1, tempoAtendimento2) -> tempoAtendimento1 + tempoAtendimento2);
			tempoMedioAtendimento.put(codigoAtendente, totalSegundosAtendimentos / totalAtendimentos);
		});
		return tempoMedioAtendimento;
	}

	@Override
	public List<Tipo> getTiposOrdenadosNumerosEventosDecrescente() {
		SortedMap<Long, Tipo> tiposMap = new TreeMap<>((valor1, valor2) -> valor2.compareTo(valor1));
		eventos
		.stream()
		.map(Evento::getTipoEvento)
		.distinct()
		.forEach(tipoEvento -> {
			Long numeroEventos = eventos.stream().filter(evento -> tipoEvento.equals(evento.getTipoEvento())).count();
			tiposMap.put(numeroEventos, tipoEvento);
		});
		return tiposMap.values().stream().collect(Collectors.toList());
	}

	@Override
	public List<Integer> getCodigoSequencialEventosDesarmeAposAlarme() {
		List<Integer> codigos = new ArrayList<>();
		eventos
		.stream()
		.map(Evento::getCodigoCliente)
		.distinct()
		.forEach(codigoCliente -> {
			List<Evento> eventosDoCliente = eventos.stream()
					.filter(evento -> codigoCliente.equals(evento.getCodigoCliente()))
					.collect(Collectors.toList());
			List<Integer> eventosDesarmeAposAlarme = eventosDoCliente
					.stream()
					.filter(evento -> Tipo.DESARME.equals(evento.getTipoEvento()))
					.filter(evento -> {
						Integer indiceEventoAnterior = eventosDoCliente.indexOf(evento) - 1;
						Evento eventoAnterior = indiceEventoAnterior != - 1 ? eventosDoCliente.get(indiceEventoAnterior) : new Evento();
						return Tipo.ALARME.equals(eventoAnterior.getTipoEvento());
					})
					.map(Evento::getCodigo)
					.collect(Collectors.toList());
			codigos.addAll(eventosDesarmeAposAlarme);
		});
		
		return codigos;
	}

}
