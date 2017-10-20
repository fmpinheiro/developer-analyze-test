package br.com.segware;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

public class AnalizadorRelatorioImpl implements IAnalisadorRelatorio {

	private RelatorioDAO relatorioDAO;
	private List<Evento> eventos;

	public AnalizadorRelatorioImpl() {
		try {
			this.relatorioDAO = new RelatorioDAO("/home/yuri/Documentos/segware/developer-test-file-analyze/src/test/java/br/com/segware/relatorio.csv");
			this.eventos = this.relatorioDAO.readFile();
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}

	@Override
	public Map<String, Integer> getTotalEventosCliente() {
		Map<String, Integer> totalEventos = new HashMap<>();
		for (Evento evento : this.eventos) {
			if (totalEventos.containsKey(evento.getCodCliente())) {
				totalEventos.put(evento.getCodCliente(), totalEventos.get(evento.getCodCliente()) + 1);
			} else {
				totalEventos.put(evento.getCodCliente(), 1);
			}

		}
		return totalEventos;
	}

	@Override
	public Map<String, Long> getTempoMedioAtendimentoAtendente() {
		Map<String, Long> tempoAtendimento = new HashMap<>();
		for (Evento evento : this.eventos) {
			if (!tempoAtendimento.containsKey(evento.getCodAtendente())) {
				tempoAtendimento.put(evento.getCodAtendente(), this.calculaMediaTempoAtendimento(evento.getCodAtendente()));
			}
		}
		return tempoAtendimento;
	}

	private Long calculaMediaTempoAtendimento(String codAtendente) {
		Long quantidadeEventos = 0L;
		Long somatorio = 0L;
		for (Evento evento : this.eventos) {
			if (evento.getCodAtendente().equals(codAtendente)) {
				quantidadeEventos++;
				somatorio += this.calculaTempo(evento.getDataInicio(), evento.getDataFim());
			}
		}
		return somatorio / quantidadeEventos;
	}

	@Override
	public List<Tipo> getTiposOrdenadosNumerosEventosDecrescente() {
		Map<Tipo, Integer> totalTipo = new HashMap<>();
		for (Evento evento : this.eventos) {
			if (totalTipo.containsKey(evento.getTipoEvento())) {
				totalTipo.put(evento.getTipoEvento(), totalTipo.get(evento.getTipoEvento()) + 1);
			} else {
				totalTipo.put(evento.getTipoEvento(), 1);
			}
		}
		List<Tipo> listaOrdenada = this.ordenaLista(totalTipo);
		return listaOrdenada;
	}

	private List<Tipo> ordenaLista(Map<Tipo, Integer> map) {
		List<Tipo> listaOrdenada = new ArrayList<>(map.keySet());
		for (int i = 0; i < listaOrdenada.size(); i++) {
			for (int j = 0; j < listaOrdenada.size(); j++) {
				if (map.get(listaOrdenada.get(i)) > map.get(listaOrdenada.get(j))) {
					Tipo temp = listaOrdenada.get(i);
					listaOrdenada.set(i, listaOrdenada.get(j));
					listaOrdenada.set(j, temp);
				}
			}
		}
		return listaOrdenada;
	}

	@Override
	public List<Integer> getCodigoSequencialEventosDesarmeAposAlarme() {
		List<Integer> listaDesarmes=new ArrayList<>();
		for(Evento alarme : eventos){
			if(Tipo.ALARME.equals(alarme.getTipoEvento())){
				for(Evento desarme : eventos){
					if(Tipo.DESARME.equals(desarme.getTipoEvento()) && desarme.getCodCliente().equals(alarme.getCodCliente())){
						if(calculaTempo(alarme.getDataInicio(), desarme.getDataFim())/60<5 && calculaTempo(alarme.getDataInicio(), desarme.getDataFim())/60>0){
							listaDesarmes.add(desarme.getCoseq());
						}
					}
				}
			}
		}
		return listaDesarmes;
	}
	
	private Long calculaTempo(DateTime dataInicio, DateTime dataFinal){
		return (dataFinal.getMillis()-dataInicio.getMillis())/1000;
	}

}
