package br.com.segware;

import br.com.hibernate.AnalisadorRelatorioHibernate;
import br.com.hibernate.DBHelper;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class AnalisadorRelatorioTest {

    IAnalisadorRelatorio analisador;

    @Before
    public void before() throws IOException {
        // analisador = sua implementação
        //executa a implementação padrão (sem banco)
        this.analisador = (IAnalisadorRelatorio) new AnalisadorRelatorio();
        /*
         Apos a insercao no banco, descomente a seguinte linha para realizar os testes 
         com Hibernate
         */
//        this.analisador = (IAnalisadorRelatorio) new AnalisadorRelatorioHibernate();
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
        assertArrayEquals(new Tipo[]{Tipo.ALARME, Tipo.DESARME, Tipo.TESTE, Tipo.ARME},
                analisador.getTiposOrdenadosNumerosEventosDecrescente().toArray(new Tipo[Tipo.values().length]));
    }

    @Test
    public void identificarEvento() {
        assertArrayEquals(new Integer[]{7}, analisador.getCodigoSequencialEventosDesarmeAposAlarme().toArray(new Integer[1]));
    }

    public static void main(String args[]) throws IOException {
        AnalisadorRelatorioTest test = new AnalisadorRelatorioTest();
        /*
         |IMPLEMENTACAO COM MySql - HIBERNATE|
         Outra solucao seria utilizar um banco de dados e obter os resultados
         atraves de consultas.
         Para isso é necessário:
         1. Criar o banco (MySql) com a sql no pacote br.com.database(
         inserindo os atendentes e clientes)
         2. Executar o método da classe DBHelper (descomentando as linhas abaixo)
         para inserir dados relacionados a Ocorrencia (conforme o arquivo CSV).
         NOTA: Esse método (adicionaOcorrencias()) só deve ser executado
         uma vez.
         */
        /*
         DBHelper db = new DBHelper();
         db.adicionaOcorrencias();
         */
    }
}
