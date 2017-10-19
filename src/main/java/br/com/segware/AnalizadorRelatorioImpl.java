package br.com.segware;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AnalizadorRelatorioImpl implements IAnalisadorRelatorio{
	
	private RelatorioDAO relatorioDAO;
	
	public AnalizadorRelatorioImpl() {
		try{
			this.relatorioDAO = new RelatorioDAO("/home/yuri/Documentos/segware/developer-test-file-analyze/src/test/java/br/com/segware/relatorio.csv");			
		}catch (FileNotFoundException e) {
			System.out.print(e.getMessage());
		}
	}

	@Override
	public Map<String, Integer> getTotalEventosCliente() {
		try{
			List<Evento> arquivo = this.relatorioDAO.readFile();			
			Map<String, Integer> totalEventos= arquivo.stream().collect(Collectors.groupingBy(Evento -> codCliente, Collectors.counting());
		}catch(Exception e){
			System.out.print(e.getMessage());
		}
		
		return null;
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
