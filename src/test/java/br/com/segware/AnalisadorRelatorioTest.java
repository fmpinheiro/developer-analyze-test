package br.com.segware;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import br.com.segware.controller.Controller;
import br.com.segware.controller.EventoController;
import br.com.segware.enums.Tipo;
import br.com.segware.model.Evento;
import br.com.segware.service.AnalisadorRelatorio;
import br.com.segware.service.IAnalisadorRelatorio;

public class AnalisadorRelatorioTest {

    IAnalisadorRelatorio analisador;
    
    private Controller<Evento> controller;
    
    private final static String RESOURCE_FILE = "src/test/resources/relatorio.csv";

    @Before
    public void before() throws IOException {
    	this.controller = new EventoController(RESOURCE_FILE);
        analisador = new AnalisadorRelatorio(this.controller);
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
        assertArrayEquals(new Integer[] { 7 }, analisador.getCodigoSequencialEventosDesarmeAposAlarme().toArray(new Integer[1]));
    }
}