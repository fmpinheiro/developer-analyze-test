/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.segware;

/**
 *
 * @author rshp
 */
public enum RelatorioEstrutura{
     CODIGO(0),
     CODIGO_CLIENTE(1),
     CODIGO_EVENTO(2),
     TIPO_EVENTO(3),
     DATA_INICIO(4),
     DATA_FINAL(5),
     CODIGO_ATENDENTE(6);

    public int valor;
    
    RelatorioEstrutura(int valor) {
        this.valor = valor;
    }
    
    public int getColuna(){
        return valor;
    }
    
}
