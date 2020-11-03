/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grafos;

/**
 *
 * @author Vinicius
 */
public class Transposicao {
      private Grafo grafo;
      
      public Grafo execute(Grafo grafo) {
         Representacao rep = grafo.getRepresentacao();
         return transposicao(rep);
      }
      
   
       public Grafo transposicao (Representacao rep){
         int i = 0;
         this.grafo = new Grafo(rep.getNumVertices(), new ListaAdjacencia()); 
         while (i < rep.numVert){
            No adj = ((ListaAdjacencia) rep).getAdjacentes(i);
            
            while (adj != null) {
                 this.grafo.addAresta(adj.getVertID(),i, 1, adj.getPeso());
                 adj = adj.getProx();
             }
            i++;
         }        
       return this.grafo;
       }
}
