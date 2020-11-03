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
public class MatrizAdjacencia extends RepresentacaoComputacional{
    private int matriz[][];
   
   public MatrizAdjacencia(int numVertices){
       super(numVertices);
       matriz = new int[numVert][numVert];
   }
   
   public void addArestaG(int u, int v, int peso){
       matriz[u][v] = peso;
       matriz[v][u] = peso;
   }
   
    public void addArestaD(int u, int v, int peso){
       matriz[u][v] = peso;       
   }
    
   public boolean verificarAdjacencia(int u, int v){
       return matriz[u][v] != 0;
   }
   
   public int valorPeso (int u, int v){
       return matriz[u][v];
   }
   
   public void exibir(){
       for(int i=0; i<numVert; i++){
           for(int j=0; j<numVert; j++){
               NovoJFrame.jTextArea1.append(matriz[i][j]+" ");
           }
           NovoJFrame.jTextArea1.append("\n");
       }
   }
}