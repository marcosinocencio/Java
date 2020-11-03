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
public class ListaAdjacencia extends RepresentacaoComputacional{
  private Lista listas[];
  public ListaAdjacencia(int numVert){
      super(numVert);
      listas = new Lista[numVert];
      for (int i=0;i<numVert;i++){
          listas[i] = new Lista();
      }
  }
  public void addArestaG(int u, int v, int peso){
      listas[u].add(v,peso);
      listas[v].add(u,peso);
  }
  
  public void addArestaD(int u, int v, int peso){
      listas[u].add(v,peso);     
  }
  
  public boolean verificarAdjacencia(int u, int v){
      return listas[u].contem(v);
  }
  
  public int valorPeso (int u, int v){
       return listas[u].Peso(v);
   }
  
  public void exibir(){
      for(int i=0; i<numVert;i++){
          NovoJFrame.jTextArea1.append("||"+i+"||-->");
          listas[i].exibir();
          NovoJFrame.jTextArea1.append("\n");
      }
  }
}
