/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grafo;

/**
 *
 * @author Vinicius
 */
public abstract class RepresentacaoComputacional {
    protected int numVert;
    public RepresentacaoComputacional(int numVert){
        this.numVert = numVert;
    }
    
    public abstract void addArestaG(int u, int v, int peso);
    public abstract void addArestaD(int u, int v, int peso);
    public abstract boolean verificarAdjacencia(int u, int v);
    public abstract int valorPeso(int u, int v);
    public abstract void exibir();
}
