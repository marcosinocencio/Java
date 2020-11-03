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
public class Grafo {
   protected int numVert;
   private RepresentacaoComputacional rep;
   public Grafo(int numVert, RepresentacaoComputacional rep){
       this.numVert = numVert;
       this.rep = rep;
   }
   
   public void addArestaG(int u, int v, int peso){
       rep.addArestaG(u, v, peso);
   }
   
   public void addArestaD(int u, int v, int peso){
       rep.addArestaD(u, v, peso);
   }
   
   public boolean verificarAdjacencia(int u, int v){
       return rep.verificarAdjacencia(u, v);
   }
   
   public int valorPeso(int u, int v){
       return rep.valorPeso(u, v);
   }
   
   public void exibir(){
       NovoJFrame.jTextArea1.append("\n\nNúmero de vértices: "+numVert+"\n\n");
       rep.exibir();
   }
}
