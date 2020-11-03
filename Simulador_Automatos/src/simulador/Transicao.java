/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador;

import java.io.Serializable;
import java.util.TreeSet;

/**
 *
 * @author Vinicius
 */
public class Transicao implements Serializable{
    private TreeSet<String> simbolo;
    private Estado estadoDestino;
    
    public Transicao (TreeSet<String> simbolo, Estado estadoDestino){
        this.simbolo = simbolo;
        this.estadoDestino = estadoDestino;    
    }
    
     public Estado getEstadoDestino() {
        return estadoDestino;
    }

    public void setEstadoDestino(Estado estadoDestino) {
        this.estadoDestino = estadoDestino;
    }    
     
     public TreeSet<String> getSimbolo() {
        return simbolo;
    } 
}
