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
public class BuscaLargura {
     public enum Cor{
        Branco, Cinza, Preto;
    } 
    
    public class Q{
        boolean X;
        int chave;
        
        Q(boolean X, int chave){
            this.chave = chave;
            this.X = X;
        }
        
        public int getChave() {
            return chave;
        }

        public void setChave(int chave) {
            this.chave = chave;
        }

        public boolean getX() {
            return X;
        }

        public void setX(boolean X) {
            this.X = X;
        }     
    }
    private ArrayList<Integer> d = new ArrayList(); //marcador do instante que  o vértice foi descoberto;
    private ArrayList<Integer> f = new ArrayList(); //marcador do instante que  o fecho  transitivo  do vértice 
                                                    //foi totalmente visitado
    private ArrayList<Integer> l_vert = new ArrayList();                              
    private ArrayList<Cor> cor = new ArrayList();
    private ArrayList<Integer> pi = new ArrayList();
    
    private Representacao r;
    
     public BuscaLargura(Grafo grafo){
      r = grafo.getRepresentacao(); 
    }
    
     public int[] BFS(int s){ //Busca em Largura 
        ArrayList<Integer> adjacentes = new ArrayList();
        int i = 0, u;         
           
         for (; i < r.numVert; i++){
            if (i != s){
                cor.add(i, Cor.Branco);
                d.add(i, Integer.MAX_VALUE);
                pi.add(i, -1);
            }
            else{
                 cor.add(s, Cor.Cinza);
                 d.add(s, 0);
                 pi.add(s, -1);
            }
           }          
            
            ArrayList<Integer> Lista = new ArrayList();
            Lista.add(s);
            
            while(!Lista.isEmpty()){
                u = Lista.get(0);
                Lista.remove(0);
                No adj = ((ListaAdjacencia) r).getAdjacentes(u);
                
            while (adj != null) {                  
                  adjacentes.add(adj.getVertID());                      
                  adj = adj.getProx();
              }                   
                             
                
                 i = 0;
                 int v;
                 while(!adjacentes.isEmpty()){                  
                     
                     v = adjacentes.get(i);
                     if (cor.get(v)== Cor.Branco){
                         cor.set(v, Cor.Cinza);
                         d.set(v, d.get(u)+1);
                         pi.set(v, u);                                              
                         Lista.add(v);                         
                    }  
                    adjacentes.remove(0);
                 }                   
                cor.set(u, Cor.Preto);
            } 
    
     
      int caminho[] = new int[r.numVert];
        
        for(i=0;i<r.numVert;i++)
              caminho[i] = pi.get(i); 
              
        
        
        d.clear(); //Limpando os ArrayLists para usar de novo na Busca com outro vértice.
        f.clear();                                                 
        l_vert.clear();                              
        cor.clear(); 
        pi.clear();  
        
        return caminho;
    }
}
