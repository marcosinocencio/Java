/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grafos;

import java.util.ArrayList;
/**
 *
 * @author Vinicius
 */
public class GrafoEuleriano {
    
    private Representacao r;
    
    public GrafoEuleriano(Grafo grafo){
      r = grafo.getRepresentacao(); 
    }
    
    public ArrayList<Integer> hierholzer (int v){//Algoritmo utilizado para encontrar o caminho/ciclo
        ArrayList<Integer> C = new ArrayList();  //em um grafo Euleriano
        ArrayList<Integer> pilha = new ArrayList();
        int[][] adj = new int[r.numVert][r.numVert];       
        int i,l,j;
        
        for(i=0;i < r.numVert; i++)
            for(j=0;j < r.numVert; j++)
                   adj[i][j] = 0;
       
        matrizAdjacencia(adj);       
        
        pilha.add(v);       
        
        while(!pilha.isEmpty()){
            l = pilha.get(pilha.size()-1);
            
            if(visitados(l,adj))
                C.add(pilha.remove(pilha.size()-1));
            else{
                for(j=0;j < r.numVert; j++){
                    if(adj[l][j] == 1){				
                        adj[l][j] = 0;
                        adj[j][l] = 0;                       
                        pilha.add(j);
                        break;			   
                    }              
                }            
            }            
        }
        return C;
    }
    
    public boolean visitados(int i, int[][] adj){ //Verifica se o vértice já foi visitado
    
        int j;
        
        for(j=0;j<r.numVert;j++){
           if(adj[i][j] == 1)
           return false;
        }
        
    return true;   
    }
    
    public void matrizAdjacencia (int[][] matriz){ // Matriz de adjacência utilizada para auxiliar
        int i;                                     //a busca do ciclo/caminho euleriano 
        
        No adj;
        i = 0;
        
        while(i < r.numVert){
            adj = ((ListaAdjacencia) r).getAdjacentes(i);
            while(adj != null){
               matriz[i][adj.getVertID()] = 1; 
               adj = adj.getProx();
            }        
          i++;
        }
    
    }
    
    public int verifica(){ // Verifica os graus dos vértices
        int i=0, grau, impar=0, par=0, inicial=0;        
        No adj;
        
        while(i < r.numVert){
          grau = 0;
          adj = ((ListaAdjacencia) r).getAdjacentes(i);
          while(adj != null){
             grau++; 
             adj = adj.getProx();
          }
          if(grau%2 == 0)
              par++;
          else{
              inicial = i;
              impar++;
          }
          i++;
        }
    if (impar == 0)      //1 - Se todos os vértices têm grau par, circuito de Euler / ciclo
        return 1;       
    else if (impar == 2) //2 - Se há exatamente 2 vértices de grau ímpar, caminho de Euler 
        return inicial;        
    else if (impar > 2 || impar == 1)  //3 - Se há mais de 2 vértices ou exatamente um vértice de grau ímpar, circuito não é possível.
        return 2;
    
    return 3;
    }
}
