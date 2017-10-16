/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.segware;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author rshp
 */
public class ImplemantaAnalisadorRelatorio implements IAnalisadorRelatorio {
    private FuncoesAdicionais funcoesAdicionais;
    private List<Relatorio> relList;
    
    public ImplemantaAnalisadorRelatorio(){
        this.funcoesAdicionais = new FuncoesAdicionais();
        this.relList = new ArrayList<>();
        this.relList = this.funcoesAdicionais.LerRelatorio("src/test/java/br/com/segware/relatorio.csv");
    }
    
    @Override
    public Map<String, Integer> getTotalEventosCliente() {
        Map<String, Integer> totalEventosCliente = new HashMap<>();
        for (Relatorio cliente : relList) {
            if (totalEventosCliente.containsKey(cliente.getCodigoCliente())){
                totalEventosCliente.put(cliente.getCodigoCliente(), totalEventosCliente.get(cliente.getCodigoCliente()) + 1 );
            }else{
                totalEventosCliente.put(cliente.getCodigoCliente(), 1);
            }
        }
        return totalEventosCliente;
    }

    @Override
    public Map<String, Long> getTempoMedioAtendimentoAtendente() {
        Long tempo;
        Map<String, Integer> quantidatePorAtendente = new HashMap<>();
        Map<String, Long> tempoMedioAtendimento = new HashMap<>();
        
        for (Relatorio atendente : relList) {
            if (tempoMedioAtendimento.containsKey(atendente.getCodigoAtendente())) {
                tempo = tempoMedioAtendimento.get(atendente.getCodigoAtendente()) + this.funcoesAdicionais.getDiferencaTempo(atendente.getDataInicial(), atendente.getDataFinal());
                tempoMedioAtendimento.put(atendente.getCodigoAtendente(), tempo);
                quantidatePorAtendente.put(atendente.getCodigoAtendente(), quantidatePorAtendente.get(atendente.getCodigoAtendente()) + 1);
            }else{
                tempo = this.funcoesAdicionais.getDiferencaTempo(atendente.getDataInicial(), atendente.getDataFinal());
                tempoMedioAtendimento.put(atendente.getCodigoAtendente(), tempo);
                quantidatePorAtendente.put(atendente.getCodigoAtendente(), 1);
            }  
        }
        for (String chave : quantidatePorAtendente.keySet()) {
            tempo = tempoMedioAtendimento.get(chave);
            tempo = this.funcoesAdicionais.getTempoMedio(tempo, quantidatePorAtendente.get(chave));
            tempoMedioAtendimento.put(chave, tempo);
        } 
        return tempoMedioAtendimento;
    }

    @Override
    public List<Tipo> getTiposOrdenadosNumerosEventosDecrescente() {
        List<Tipo> ordemEventos = new ArrayList<>();
        Map<Tipo, Integer> map = new HashMap<>();
        Map<Integer, Tipo> auxMap = new TreeMap<>();
        for (Relatorio eventos : relList) {
            if (map.containsKey(eventos.getTipoEvento())) {
                map.put(eventos.getTipoEvento(),map.get(eventos.getTipoEvento()) + 1);
            }else{
                map.put(eventos.getTipoEvento(), 1);
            }
        }
        for (Tipo tipo : map.keySet()) {
            auxMap.put(map.get(tipo), tipo);
        }
        for(Map.Entry<Integer,Tipo> entry : auxMap.entrySet()) {
            ordemEventos.add(entry.getValue());
        } 
        Collections.reverse(ordemEventos);
        return ordemEventos;
    }

    @Override
    public List<Integer> getCodigoSequencialEventosDesarmeAposAlarme() {
        List<Integer> desarmeAposAlarme = new ArrayList<>();
        Integer aux =0;
        
        for (Relatorio relatorio : relList) {
            if (relatorio.getTipoEvento() == Tipo.DESARME) {
                ListIterator lista = relList.listIterator(aux);
                Relatorio relatorioAux;
                while(lista.hasPrevious()) {
                    relatorioAux = (Relatorio) lista.previous(); 
                    if (this.funcoesAdicionais.getDiferencaTempo(relatorioAux.getDataInicial(), relatorio.getDataInicial()) > 300){break;}
                    if (relatorioAux.getCodigoCliente().equals(relatorio.getCodigoCliente()) 
                        && relatorioAux.getTipoEvento() == Tipo.ALARME) {
                            desarmeAposAlarme.add(Integer.parseInt(relatorio.getCodigo()));
                            break;
                    } 
                }
            }
            aux++;
        }
        return desarmeAposAlarme;
    }
}
