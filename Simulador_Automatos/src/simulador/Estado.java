/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Vinicius
 */
public class Estado implements Serializable{
    private String nome;
    private boolean inicial;
    private boolean estFinal;
    private ArrayList< Transicao > transicoes;  
    private int xCentral;
    private int yCentral;
    private Point posicaoSaida; 
    private String saida;
    private int id = 0;
    
    public Estado( String nome, boolean inicial, boolean estFinal, Point posicaoSaida ) {
        this.nome = nome;
        this.inicial = inicial;
        this.estFinal = estFinal;
        this.posicaoSaida = posicaoSaida;
        transicoes = new ArrayList<>();
    }

    public boolean isFinal() {
        return estFinal;
    }

    public boolean isInicial() {
        return inicial;
    }

    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Transicao> getTransicoes() {
        return transicoes;
    }  

    public void setTransicoes(ArrayList<Transicao> transicoes) {
        this.transicoes = transicoes;
    }    
    
    public void setFinal( boolean estFinal ) {
        this.estFinal = estFinal;
    }
    
    public void setInicial( boolean inicial ) {
        this.inicial = inicial;
    }
    
    public int getXCentral() {
        return xCentral;
    }

    public void setXCentral(int xCentral) {
        this.xCentral = xCentral;
    }

    public int getYCentral() {
        return yCentral;
    }

    public void setYCentral(int yCentral) {
        this.yCentral = yCentral;
    }

    public Point getPosicaoSaida() {
        return posicaoSaida;
    }

    public void setPosicaoSaida(Point posicaoSaida) {
        this.posicaoSaida = posicaoSaida;
    }

    public String getSaida() {
        return saida;
    }

    public void setSaida(String saida) {
        this.saida = saida;
    } 
    
    public void setId(int id) {
        this.id = id;
    }

    public String getId() {
        return String.valueOf(id);
    }
    
}
