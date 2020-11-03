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
public class OrdemTopologica {
    private Representacao r; 
    ArrayList<Integer> Fila = new ArrayList();
    
    public OrdemTopologica(Grafo grafo){
      r = grafo.getRepresentacao(); 
    }
    
    public ArrayList<Integer> Topologica (){
        ArrayList<Integer> ordem = new ArrayList();
        ArrayList<Integer> fila = new ArrayList();
        ArrayList<Integer> grau = new ArrayList();
        int i = 0, k = 0;
        No adj,aux;
        
        while(i < r.numVert){ //Marcar os graus de saidas dos vertices
            adj = ((ListaAdjacencia) r).getAdjacentes(i);
            int j = 0;
            while(k < r.numVert){
                adj = ((ListaAdjacencia) r).getAdjacentes(k);
                while (adj != null) {                   
                      if(adj.getVertID() == i)
                         j++;
                      adj = adj.getProx();
                  }                 
                k++;
            }
            grau.add(i, j);
            k=0;            
            i++;
        }
        
        i = 0;
        
        while(i < grau.size()){ //Grau de entrada 0 na fila
            if(grau.get(i)==0) 
                 fila.add(i);
            i++;
        }        
       
       
       while(!fila.isEmpty()){ //Vertices Grau 0 na Fila
           adj = ((ListaAdjacencia) r).getAdjacentes(fila.get(0));
           aux = adj;
           ordem.add(fila.get(0));          
           fila.remove(0);           
           while(adj != null){ // Diminui o Grau de adjacencia            
              if(grau.get(adj.getVertID()) > 0)
                grau.set(adj.getVertID(), (grau.get(adj.getVertID())-1) );  
             
              adj = adj.getProx();               
           }       
          
          
           while(aux != null){ //Grau de entrada 0 na fila
               if(grau.get(aux.getVertID()) == 0) {
                    fila.add(aux.getVertID());                  
               }
              aux = aux.getProx();
           }      
       }      
    
    return ordem;
    }
    
    public boolean verificaCiclo (){
        boolean[] visitado = new boolean[r.numVert];
        boolean[] pilha = new boolean[r.numVert];
        
        for(int i=0; i < visitado.length; i++){
           visitado[i] = false;
           pilha[i] = false;
        }
        
        for (int u = 0; u <  visitado.length; u++)
            if (ciclo(u,visitado,pilha))             
                return true;
        
        return false;
    }
    
    public boolean ciclo (int v, boolean[] visitado, boolean[] pilha){
             
        No adj = ((ListaAdjacencia) r).getAdjacentes(v);
       
        if(visitado[v] == false){
            
            visitado[v] = true;
            pilha[v] = true;
            
            while (adj != null){        

                if (!visitado[adj.getVertID()] && ciclo(adj.getVertID(), visitado, pilha))               
                     return true;
                else if(pilha[adj.getVertID()])
                     return true;    
                
                adj = adj.getProx();
            }
        }
        pilha[v] = false;
        return false;
    }
    
}
