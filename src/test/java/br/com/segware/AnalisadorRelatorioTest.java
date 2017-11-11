package br.com.segware;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AnalisadorRelatorioTest {

	IAnalisadorRelatorio analisador;
	static String pathArquivo = "relatorio.csv";
	static List<Evento> eventos;

	@BeforeClass
	public static void setUpOnce() {
		LeitorArquivo leitorArquivo = new LeitorArquivo();
		try {
			Path path = Paths.get(AnalisadorRelatorioTest.class.getClassLoader().getResource(pathArquivo).getFile());
			eventos = leitorArquivo.lerEventosDoArquivo(path);
		} catch (NullPointerException e) {
			fail("Arquivo de eventos não encontrado.");
		} catch (IOException e) {
			fail("Erro ao carregar/ler arquivo de eventos.");
		}
	}

	@Before
	public void before() {
		analisador = new AnalisadorRelatorio(eventos);
	}

	@Test
	public void totalDeEventosDoCliente0001() {
		assertEquals(7, analisador.getTotalEventosCliente().get("0001"), 0);
	}

	@Test
	public void totalDeEventosDoCliente0003() {
		assertEquals(3, analisador.getTotalEventosCliente().get("0003"), 0);
	}

	@Test
	public void tempoMedioDeAtendimentoEmSegundosDoAtendenteAT01() {
		assertEquals(159, analisador.getTempoMedioAtendimentoAtendente().get("AT01"), 0);
	}

	@Test
	public void tempoMedioDeAtendimentoEmSegundosDoAtendenteAT02() {
		assertEquals(156, analisador.getTempoMedioAtendimentoAtendente().get("AT02"), 0);
	}

	@Test
	public void tipoComMaisEventos() {
		assertArrayEquals(new Tipo[] { Tipo.ALARME, Tipo.DESARME, Tipo.TESTE, Tipo.ARME },
				analisador.getTiposOrdenadosNumerosEventosDecrescente().toArray(new Tipo[Tipo.values().length]));
	}

	@Test
	public void identificarEvento() {
		assertArrayEquals(new Integer[] { 7 },
				analisador.getCodigoSequencialEventosDesarmeAposAlarme().toArray(new Integer[1]));
	}
}