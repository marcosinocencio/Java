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
public class AGM {
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
    
     public AGM(Grafo grafo){
      r = grafo.getRepresentacao(); 
    }
     
    public boolean vazioQ(ArrayList<Q> q){ //Verifica se o conjunto Q está vazio
            int i=0; Q aux;            
          
             
            for(;i < q.size(); i++){
                aux = q.get(i);               
                if(aux.X == true)
                    return false;
            }
            return true;
        }
    
     public int[] AGM_Prim(){ //Sempre começa do primeiro vértice
        ArrayList<Integer> adjacentes = new ArrayList();
        ArrayList<Q> q = new ArrayList();       
        Q aux;
        int i = 0, u;       
         
        for (; i < r.numVert; i++){
            pi.add(i, -1);             
            q.add(new Q(true,Integer.MAX_VALUE));//Arestas que não pertencem ao conjunto X (Todas no início).
        }
        
        aux = q.get(0);        
        aux.chave = 0;
        q.set(0,aux);
        
        while(!vazioQ(q)){ //Enquanto o Conjunto Q não for vazio faça
            aux = extrairMinimo(q);
            u = q.indexOf(aux);
            
        No adj = ((ListaAdjacencia) r).getAdjacentes(u);
            
             
        while (adj != null) {   //Pega os vértices adjacentes de u
              adjacentes.add(adj.getVertID());                      
              adj = adj.getProx();
          }  
           
            adj = ((ListaAdjacencia) r).getAdjacentes(u);
            while(!adjacentes.isEmpty()){    //Verifica todos os vértices adjacentes de u          
                aux = q.get(adjacentes.get(0));
                 
                if(aux.X == true && ( adj.getPeso() < aux.chave)){
                    aux.chave = adj.getPeso();
                    pi.set(adjacentes.get(0), u);
                }
                adj = adj.getProx();
                adjacentes.remove(0);
            }
            
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
     
     public Q extrairMinimo (ArrayList<Q> Q){
        int min=0, i=0,j=0; Q aux;
      
         for(;i < Q.size(); i++){ //pegar a primeira chave que pertence a Q
            aux = Q.get(i);           
            if (aux.X == true){                
                min = aux.chave;
                break;
            }
         }        
     
     
       
        for(i=0 ; i < Q.size(); i++){ //Verificar qual é a menor chave de Q
            aux = Q.get(i);           
            if (aux.chave <= min && aux.X == true){                 
                min = aux.chave;                
                j = i;
            }           
        } 
        
        aux = Q.get(j);
        aux.X = false;
        Q.set(j, aux);
        return aux;
    } 
}
