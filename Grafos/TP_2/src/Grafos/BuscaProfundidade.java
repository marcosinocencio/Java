/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grafos;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
/**
 *
 * @author Vinicius
 */
public class BuscaProfundidade {
    
    private ArrayList<Integer> d = new ArrayList(); //marcador do instante que  o vértice foi descoberto;
    private ArrayList<A> f = new ArrayList(); //marcador do instante que  o fecho  transitivo  do vértice 
                                                        //foi totalmente visitado
    private ArrayList<Integer> l_vert = new ArrayList();                              
    private ArrayList<Cor> cor = new ArrayList();

    private Representacao r;
    private int tempo;
    private static ArrayList<A> ordem;
    
    public enum Cor{
        Branco, Cinza, Preto;
    } 
    
    public class A {
        private int posicao;
        private int f;
        
        public A (int posicao, int f){
            this.posicao = posicao;
            this.f = f;        
        }

        public int getPosicao() {
            return posicao;
        }

        public void setPosicao(int posicao) {
            this.posicao = posicao;
        }

        public int getF() {
            return f;
        }

        public void setF(int f) {
            this.f = f;
        }
        
    }
    
    public BuscaProfundidade(Grafo grafo){
      r = grafo.getRepresentacao(); 
    }

    public ArrayList<Integer> DFS(int u, ArrayList<Integer> vetor, int vez){   //Busca em profundidade   
        int i = 0, f_conexa = 0; 
        ArrayList<Integer> conexa; 
        
        if(vez == 1){
            l_vert.add(0,u);   
            A n = new A(0,0);
            for (; i < r.numVert; i++){
                cor.add(Cor.Branco);
                d.add(0);
                n.f = 0;
                n.posicao = i;
                f.add(0, n);
                if (i != u)
                   l_vert.add(i);
            }
        }
        
        else{
            A n = new A(0,0);
            for(i=0; i < vetor.size(); i++){
                l_vert.add(i, vetor.get(i)); 
                cor.add(Cor.Branco);
                d.add(0);
                n.f = 0;
                n.posicao = i;
                f.add(0, n);               
            }
        }
        
        tempo = 0;
       
        conexa = new ArrayList();
      
        while(!l_vert.isEmpty()){
            u = l_vert.get(0);           
            if (cor.get(u) == Cor.Branco) {             
                DFS_VISIT(u);                
                f_conexa++;   
            }
            conexa.add(f_conexa);
            l_vert.remove(0);
        }       
     
       if(vez != 1)
           return conexa;
       
      
      Collections.sort (f, new Comparator() { // Ordena A_Linha
                            public int compare(Object o1, Object o2) {
                                A n1 = (A) o1;
                                A n2 = (A) o2;
                                return n1.f > n2.f ? -1 : (n1.f < n2.f ? +1 : 0);
                        }
         });         
     
      ArrayList<Integer> ordem = new ArrayList();
     
         
        for(i=0; i < f.size(); i++)           
            ordem.add(i, f.get(i).posicao);        
         
   
   
     d.clear(); //Limpando os ArrayLists para usar de novo na Busca com outro vértice.
     f.clear();                                                 
     l_vert.clear();                              
     cor.clear(); 
     return ordem;
    }
    
    public void DFS_VISIT(int u){ // Busca em Profundidade (Função Visita)
        
        ArrayList<Integer> adjacentes = new ArrayList();
        int i = 0;
        cor.set(u, Cor.Cinza);
        tempo++;
        d.set(u, tempo);
        
        No adj = ((ListaAdjacencia) r).getAdjacentes(u);

        while (adj != null) {                  
               adjacentes.add(adj.getVertID());                      
               adj = adj.getProx();
         } 
        
   
         while (!adjacentes.isEmpty()){
            i = adjacentes.get(0);
            if (cor.get(i) == Cor.Branco)
                DFS_VISIT(i);                
            
            adjacentes.remove(0);
         } 
         A n = new A(0,0);        
         cor.set(u, Cor.Preto);
         tempo++;
         n.f = tempo;
         n.posicao = u;
         f.set(u, n);
    }
}
