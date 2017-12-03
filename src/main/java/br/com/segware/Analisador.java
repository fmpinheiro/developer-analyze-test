package br.com.segware;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class Analisador implements IAnalisadorRelatorio {
	private List<Evento> eventoOut = null;
	private static final long TEMPO_LIMITE = 300;

	public Analisador() {
		try {
			Reader reader = new Reader();
			reader.inputReader();
			this.eventoOut = new ArrayList<Evento>();

			List<String[]> input = reader.getList();
			for (int i = 0; i < input.size(); i++) {
				Evento e = new Evento(input.get(i));
				this.eventoOut.add(e);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public Map<String, Integer> getTotalEventosCliente() {
		Map<String, Integer> aux = new HashMap<String, Integer>();
		List<String> clientes = getListaClientes();
		for (String cli : clientes) {
			int sumEventos = 0;
			for (Evento e : eventoOut) {
				if (e.getCodCliente().equals(cli)) {
					sumEventos++;
				}
				aux.put(cli, sumEventos);
			}
		}
		return aux;
	}

	@Override
	public Map<String, Long> getTempoMedioAtendimentoAtendente() {
		Map<String, Long> aux = new HashMap<String, Long>();
		List<String> listaAtds = getAtds();

		for (String tempAtd : listaAtds) {
			long AtdQtd = 0;
			long segs = 0;
			for (Evento e : eventoOut) {
				if (e.getCodAtd().equals(tempAtd)) {
					AtdQtd++;
					segs += calcDifTempo(e.getDataIni(), e.getDataFim());
				}
			}
			aux.put(tempAtd, (segs / AtdQtd));
		}
		return aux;
	}
 
	@Override
	public List<Tipo> getTiposOrdenadosNumerosEventosDecrescente() {
		List<Tipo> tipos = Arrays.asList(Tipo.values());
		List<Tipo> aux = new ArrayList<Tipo>();
		SortedMap<Integer, Tipo> map = new TreeMap<>(Collections.reverseOrder());
		for (Tipo t : tipos) {
			int eventosSum = 0;
			for (Evento e : eventoOut) {
				if (e.getTipoEvento().equals(t)) {
					eventosSum++;
				}
			}
			map.put(eventosSum, t);
		}
		for (Iterator<Integer> i = map.keySet().iterator(); i.hasNext();) {
			Integer n = i.next();
			aux.add(map.get(n));
		}
		return aux;
	}

	@Override
	public List<Integer> getCodigoSequencialEventosDesarmeAposAlarme() {
		Evento atual = null;
		Evento anterior = null;
		List<Integer> aux = new ArrayList<>();
		ordenaLista();

		for (int i = 1; i < eventoOut.size(); i++) {
			atual = eventoOut.get(i);
			anterior = eventoOut.get(i - 1);
			if (alarmeSeguidoDesarme(atual.getTipoEvento(), anterior.getTipoEvento())
					&& anterior.getCodCliente().equals(atual.getCodCliente())
					&& (tempoMax(anterior.getDataIni(), atual.getDataIni()))) {
				aux.add(atual.getCodSequencial());
			}
		}
		return aux;
	}

	private List<Integer> ordenaLista() {
		List<Integer> aux = new ArrayList<>();
		eventoOut.sort(Comparator.comparing(Evento::getCodCliente));

		return aux;
	}

	private List<String> getListaClientes() {
		List<String> auxTemp = new ArrayList<String>();
		for (Evento e : eventoOut) {
			auxTemp.add(e.getCodCliente());
		}
		return auxTemp;
	}

	private List<String> getAtds() {
		List<String> auxTemp = new ArrayList<String>();
		for (Evento e : eventoOut) {
			auxTemp.add(e.getCodAtd());
		}
		return auxTemp;
	}

	private long calcDifTempo(LocalDateTime dataIni, LocalDateTime dataFim) {
		return ChronoUnit.SECONDS.between(dataIni, dataFim);
	}

	private boolean tempoMax(LocalDateTime dataIni, LocalDateTime dataFim) {
		if (calcDifTempo(dataIni, dataFim) <= TEMPO_LIMITE) {
			return true;
		}
		return false;
	}

	private boolean alarmeSeguidoDesarme(Tipo a, Tipo b) {
		if (a.equals(Tipo.DESARME) && b.equals(Tipo.ALARME)) {
			return true;
		}
		return false;
	}

}
