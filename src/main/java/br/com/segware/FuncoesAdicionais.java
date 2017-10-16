/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.segware;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author rshp
 */
public class FuncoesAdicionais {
    
    public Long getTempoMedio(long tempo, long quantidade ){
        return tempo/quantidade;       
    }
    
    public Long getDiferencaTempo(String dataInicial, String dataFinal){
        Long tempo;
        Long tempoInicial;
        Long tempoFinal;
        tempoInicial = getTempo(dataInicial);
        tempoFinal = getTempo(dataFinal);
        tempo = tempoFinal - tempoInicial;
        tempo = tempo/1000;
        return tempo;
    }

    public Long getTempo(String dataInicial) {
       Date data = new Date();
        SimpleDateFormat formatarData = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       try{
           data = formatarData.parse(dataInicial);
       }catch (ParseException e ){
           System.out.println("Erro ao formatar Data");
       }
       formatarData.format(data);
       return data.getTime();
    }
    
    public List LerRelatorio(String caminho){
        List<Relatorio> relList = new ArrayList<>();
        String csvFile = caminho;
        String linha;
        String separarPor = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((linha = br.readLine()) != null) {
                Relatorio relatorio = new Relatorio();
                String[] desc = linha.split(separarPor);
                relatorio.setCodigo(desc[RelatorioEstrutura.CODIGO.getColuna()]);
                relatorio.setCodigoCliente(desc[RelatorioEstrutura.CODIGO_CLIENTE.getColuna()]);
                relatorio.setCodigoEvento(desc[RelatorioEstrutura.CODIGO_EVENTO.getColuna()]);
                relatorio.setTipoEvento(Tipo.valueOf(desc[RelatorioEstrutura.TIPO_EVENTO.getColuna()]));
                relatorio.setDataInicial(desc[RelatorioEstrutura.DATA_INICIO.getColuna()]);
                relatorio.setDataFinal(desc[RelatorioEstrutura.DATA_FINAL.getColuna()]);
                relatorio.setCodigoAtendente(desc[RelatorioEstrutura.CODIGO_ATENDENTE.getColuna()]);
                relList.add(relatorio);              
            }
            br.close();
        }catch (IOException e) {
            System.out.println("Erro ao ler arquivo! ->"+e);
        }
        
        return relList;
    }
}
