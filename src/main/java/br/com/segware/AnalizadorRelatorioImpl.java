package br.com.segware;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AnalizadorRelatorioImpl implements IAnalisadorRelatorio {

	private RelatorioDAO relatorioDAO;

	public AnalizadorRelatorioImpl() {
		try {
			this.relatorioDAO = new RelatorioDAO("/home/yuri/Documentos/segware/developer-test-file-analyze/src/test/java/br/com/segware/relatorio.csv");
		} catch (FileNotFoundException e) {
			System.out.print(e.getMessage());
		}
	}

	@Override
	public Map<String, Integer> getTotalEventosCliente() {
		Map<String, Integer> totalEventos = new HashMap<>();
		try {
			List<Evento> eventos = this.relatorioDAO.readFile();
			for (Evento evento : eventos) {
				if (totalEventos.containsKey(evento.getCodCliente())) {
					totalEventos.put(evento.getCodCliente(), totalEventos.get(evento.getCodCliente()) + 1);
				} else {
					totalEventos.put(evento.getCodCliente(), 1);

				}
			}
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		return totalEventos;
	}

	@Override
	public Map<String, Long> getTempoMedioAtendimentoAtendente() {
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
