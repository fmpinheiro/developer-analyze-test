package br.com.segware;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.joda.time.Seconds;

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
				somatorio += (evento.getDataFim().getMillis() - evento.getDataInicio().getMillis())/1000; 
			}
		}
		return somatorio/quantidadeEventos;
	}

	@Override
	public List<Tipo> getTiposOrdenadosNumerosEventosDecrescente() {
	}

	@Override
	public List<Integer> getCodigoSequencialEventosDesarmeAposAlarme() {
		// TODO Auto-generated method stub
		return null;
	}

}
