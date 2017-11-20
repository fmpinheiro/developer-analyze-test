package br.com.segware;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.joda.time.DateTime;

public class Analisador implements IAnalisadorRelatorio {

	private List<Evento> relatorio;

	public Analisador() throws IOException {
		LeitorIO leitor = new LeitorIO();
		List<String[]> arqLido;
		this.relatorio = new ArrayList<Evento>();

		arqLido = leitor.lerArquivo();
		for (int i = 0; i < arqLido.size(); i++) {
			Evento ei = new Evento(arqLido.get(i));
			this.relatorio.add(ei);
		}
	}

	@Override
	public Map<String, Integer> getTotalEventosCliente() {
		Map<String, Integer> resultado = new HashMap<String, Integer>();
		List<String> clientes = getListaClientes();

		for (String cli : clientes) {
			resultado.put(cli, calcTotEventDoCliente(cli));
		}

		return resultado;
	}

	@Override
	public Map<String, Long> getTempoMedioAtendimentoAtendente() {
		Map<String, Long> resultado = new HashMap<String, Long>();
		List<String> atendentes = getListaAtendentes();

		for (String ate : atendentes) {
			resultado.put(ate, calcTempoMedio(ate));
		}

		return resultado;
	}

	@Override
	public List<Tipo> getTiposOrdenadosNumerosEventosDecrescente() {
		List<Tipo> tiposExistente = Arrays.asList(Tipo.values());
		List<Tipo> resultado = new ArrayList<>();
		SortedMap<Integer, Tipo> map = new TreeMap<>(Collections.reverseOrder());

		for (Tipo t : tiposExistente) {
			map.put(calcTotEventDoTipo(t), t);
		}

		for (Iterator<Integer> i = map.keySet().iterator(); i.hasNext();) {
			Integer n = i.next();
			resultado.add(map.get(n));
		}

		return resultado;
	}

	@Override
	public List<Integer> getCodigoSequencialEventosDesarmeAposAlarme() {
		List<Integer> resultado = new ArrayList<>();

		// Ordenamos os eventos do relatório pelo Cliente
		relatorio.sort(Comparator.comparing(Evento::getCodCliente));

		for (int i = 1; i < relatorio.size(); i++) {
			Evento evAtual = relatorio.get(i);
			Evento evAnter = relatorio.get(i - 1);
			// Verifica se o evento Atual ocorreu apos um evento de alarme.
			if (evAtual.getTipoEvento().equals(Tipo.DESARME) && evAnter.getTipoEvento().equals(Tipo.ALARME) &&
			// Verifica se e o evento de anterior e o atual são no mesmo cliente.
					evAnter.getCodCliente().equals(evAtual.getCodCliente())
					// Verifica se o tempo entre o alarme e o desarme não ultrapassou os 5 minutos
					&& (calcDifTempo(evAnter.getDataIni(), evAtual.getDataIni()) <= 300)) {
				resultado.add(evAtual.getCodSequencial());
			}
		}
		return resultado;
	}

	private List<String> getListaClientes() {
		Set<String> setCli = new HashSet<>();

		for (Evento e : relatorio) {
			setCli.add(e.getCodCliente());
		}

		List<String> listClientes = new ArrayList<>(setCli);

		return listClientes;
	}

	private int calcTotEventDoCliente(String codCli) {
		int totalEventos = 0;

		for (Evento e : relatorio) {
			if (e.getCodCliente().equals(codCli)) {
				totalEventos++;
			}
		}

		return totalEventos;
	}

	private List<String> getListaAtendentes() {
		Set<String> setAte = new HashSet<String>();

		for (Evento e : relatorio) {
			setAte.add(e.getCodAtendent());
		}

		ArrayList<String> atendents = new ArrayList<String>(setAte);
		return atendents;
	}

	private long calcTempoMedio(String codAtendent) {
		long totAtends = 0;
		long totTime = 0;

		for (Evento e : relatorio) {
			if (e.getCodAtendent().equals(codAtendent)) {
				DateTime dataIni = e.getDataIni();
				DateTime dataFim = e.getDataFim();
				totTime = totTime + calcDifTempo(dataIni, dataFim);
				totAtends++;
			}
		}
		return (totTime / totAtends);
	}

	private long calcDifTempo(DateTime dataIni, DateTime dataFim) {
		long difTempo = ((dataFim.getMillis() - dataIni.getMillis()) / 1000);
		return difTempo;
	}

	private int calcTotEventDoTipo(Tipo tipoEvento) {
		int totalEventos = 0;

		for (Evento e : relatorio) {
			if (e.getTipoEvento().equals(tipoEvento)) {
				totalEventos++;
			}
		}
		return totalEventos;
	}

}