/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grafo;
import java.util.*;

/**
 *
 * @author Vinicius
 */
public class Algoritmos {
    
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
    
    public class A{ //Usado para o conjunto A (Algoritmo de Kruskal)
        
        int u,v, peso;
        
        A(int u, int v, int peso){
            this.u = u;
            this.v = v;
            this.peso = peso;
        }
        
        public int getU() {
            return u;
        }

        public void setU(int u) {
            this.u = u;
        }

       public int getV() {
            return v;
        }

        public void setV(int v) {
            this.v = v;
        } 
        
         public int getPeso() {
            return peso;
        }

        public void setPeso(int peso) {
            this.peso = peso;
        }
    
    }
    
    private ArrayList<Integer> d = new ArrayList(); //marcador do instante que  o vértice foi descoberto;
    private ArrayList<Integer> f = new ArrayList(); //marcador do instante que  o fecho  transitivo  do vértice 
                                                    //foi totalmente visitado
    private ArrayList<Integer> l_vert = new ArrayList();                              
    private ArrayList<Cor> cor = new ArrayList();
    private ArrayList<Integer> pi = new ArrayList();
    private int tempo; 
    private Grafo grafo; //Grafo  
    
    public Algoritmos(Grafo g){
        this.grafo = g;        
    }
    
    public void DFS(int u){   //Busca em profundidade   
        int i = 0,j;
        j = u;
        l_vert.add(0,u);   
        
        for (; i < grafo.numVert; i++){
            cor.add(Cor.Branco);
            d.add(0);
            f.add(0);
            if (i != u)
               l_vert.add(i);
        }
        tempo = 0;
        
        while(!l_vert.isEmpty()){
            u = l_vert.get(0);
            if (cor.get(u) == Cor.Branco)              
                DFS_VISIT(u);
            
            l_vert.remove(0);
        } 
        
        NovoJFrame.jTextArea1.append("\n");
        NovoJFrame.jTextArea1.append("\nBusca em Profundidade com início no vértice["+j+"]\n\n");
        for(i=0; i<grafo.numVert;i++){
                 NovoJFrame.jTextArea1.append("Vertice["+i+"]:"+" Tempo de Chegada(Cinza): "+d.get(i)+"      Tempo de Finalização(Preto): "+ f.get(i) );
                 NovoJFrame.jTextArea1.append("\n");   
            }
        
     d.clear(); //Limpando os ArrayLists para usar de novo na Busca com outro vértice.
     f.clear();                                                 
     l_vert.clear();                              
     cor.clear(); 
     pi.clear(); 
        
    }
    
    public void DFS_VISIT(int u){ // Busca em Profundidade (Função Visita)
        
        ArrayList<Integer> adjacentes = new ArrayList();
        int i = 0;
        cor.set(u, Cor.Cinza);
        tempo++;
        d.set(u, tempo);
        
         for (; i < grafo.numVert; i++){
            if (grafo.verificarAdjacencia(u, i))
                adjacentes.add(i);
         }
        
   
         while (!adjacentes.isEmpty()){
            i = adjacentes.get(0);
            if (cor.get(i) == Cor.Branco)
                DFS_VISIT(i);                
            
            adjacentes.remove(0);
         } 
         
         cor.set(u, Cor.Preto);
         tempo++;
         f.set(u, tempo);
    }
    
    public void BFS(int s){ //Busca em Largura 
        ArrayList<Integer> adjacentes = new ArrayList();
        int i = 0, u,j;         
         j=s;   
         for (; i < grafo.numVert; i++){
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
                
                for (i=0; i < grafo.numVert; i++){
                   if (grafo.verificarAdjacencia(u, i))
                        adjacentes.add(i);
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
            
        NovoJFrame.jTextArea1.append("\n");
        NovoJFrame.jTextArea1.append("\nBusca em Largura com início no vértice["+j+"]\n\n");
        for(i=0; i<grafo.numVert;i++){
                 NovoJFrame.jTextArea1.append("Vertice["+i+"]:"+" Predecessor(Pai): "+pi.get(i)+"      Distância até a raiz: "+ d.get(i) );
                 NovoJFrame.jTextArea1.append("\n");   
            }
        
     d.clear(); //Limpando os ArrayLists para usar de novo na Busca com outro vértice.
     f.clear();                                                 
     l_vert.clear();                              
     cor.clear(); 
     pi.clear();           
    }
    
    public void AGM_Prim(){ //Sempre começa do primeiro vértice
        ArrayList<Integer> adjacentes = new ArrayList();
        ArrayList<Q> q = new ArrayList();       
        Q aux;
        int i = 0, u;       
         
        for (; i < grafo.numVert; i++){
            pi.add(i, -1);             
            q.add(new Q(true,Integer.MAX_VALUE));//Arestas que não pertencem ao conjunto X (Todas no início).
        }
        
        aux = q.get(0);        
        aux.chave = 0;
        q.set(0,aux);
        
        while(!vazioQ(q)){ //Enquanto o Conjunto Q não for vazio faça
            aux = extrairMinimo(q);
            u = q.indexOf(aux);
            
            i=0;
            for (; i < grafo.numVert; i++){  //Pega os vértices adjacentes de U
               if (grafo.verificarAdjacencia(u, i))
                   adjacentes.add(i);
            }
           
                      
            while(!adjacentes.isEmpty()){    //Verifica todos os vértices adjacentes de u          
                aux = q.get(adjacentes.get(0));
                 
                if(aux.X == true && ( grafo.valorPeso(u, adjacentes.get(0)) < aux.chave)){
                    aux.chave = grafo.valorPeso(u, adjacentes.get(0));
                    pi.set(adjacentes.get(0), u);
                }
                adjacentes.remove(0);
            }
            
         }
          NovoJFrame.jTextArea1.append("\n");
          NovoJFrame.jTextArea1.append("\nÁrvore geradora mínima utilizando o algoritmo de Prim\n\n");
          
          for(i=0; i<grafo.numVert;i++){
		aux = q.get(i);
                NovoJFrame.jTextArea1.append("Vertice["+i+"]:"+" Predecessor(Pai): "+pi.get(i)+"      chave: "+ aux.chave );
                NovoJFrame.jTextArea1.append("\n");   
           }

            d.clear(); //Limpando os ArrayLists para usar de novo na Busca com outro vértice.
            f.clear();                                                 
            l_vert.clear();                              
            cor.clear(); 
            pi.clear();  
         
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
    
    public boolean vazioQ(ArrayList<Q> q){ //Verifica se o conjunto Q está vazio
            int i=0; Q aux;            
          
             
            for(;i < q.size(); i++){
                aux = q.get(i);               
                if(aux.X == true)
                    return false;
            }
            return true;
        }
    
    public void AGM_Kruskal(){ //Sempre começa do primeiro vértice
        ArrayList<ArrayList<Integer>> A = new ArrayList();
        ArrayList<A> A_Linha = new ArrayList();
        ArrayList<A> X = new ArrayList();
        int i=0; A aux;   
       
        
        criarConjunto(A);// Cria conjunto para cada vértice
        
        
        for(; i < grafo.numVert ;i++){ //Adiciona as aresta à A_Linha
            for(int j=0; j < grafo.numVert ;j++)
                if(grafo.verificarAdjacencia(i, j)){
                    aux = new A(i,j, grafo.valorPeso(i, j));                    
                    A_Linha.add(aux);                    
                }                    
        }
        
        Collections.sort (A_Linha, new Comparator() { // Ordena A_Linha
                            public int compare(Object o1, Object o2) {
                            A p1 = (A) o1;
                            A p2 = (A) o2;
                            return p1.peso < p2.peso ? -1 : (p1.peso > p2.peso ? +1 : 0);
                        }
         }); 
        
         int j;
         for(i = 0; i < A_Linha.size() ;i++){ //Remove Arestas Repetidas de A_Linha (Grafo A->B == B->A)
               A aux2;  
               
               aux = A_Linha.get(i);
               for(j=1; j < A_Linha.size(); j++){
                    aux2 = A_Linha.get(j);
                    if (aux.u == aux2.v && aux.v == aux2.u)
                      A_Linha.remove(j);
               }
         }       
         
         while(!A_Linha.isEmpty()){  //Para cada Aresta de A_Linha              
              if(comparaConjunto(A, A_Linha.get(0).u, A_Linha.get(0).v)){ // Se for Conjuntos diferentes
                   uniãoConjunto(A, A_Linha.get(0).u, A_Linha.get(0).v);   //Faz a união                 
                   X.add(A_Linha.get(0));     //Adiciona à X              
              }              
              A_Linha.remove(0);
         }  
          NovoJFrame.jTextArea1.append("\n");
          NovoJFrame.jTextArea1.append("\nÁrvore geradora mínima utilizando o algoritmo de Kruskal\n\n");
          
          for(i=0; i<X.size();i++){
                NovoJFrame.jTextArea1.append("Aresta: "+X.get(i).u+" "+X.get(i).v+"     peso: "+ grafo.valorPeso(X.get(i).u, X.get(i).v) );
                NovoJFrame.jTextArea1.append("\n");   
           }

            d.clear(); //Limpando os ArrayLists para usar de novo na Busca com outro vértice.
            f.clear();                                                 
            l_vert.clear();                              
            cor.clear(); 
            pi.clear();   
        
    }
    
    public boolean comparaConjunto (ArrayList<ArrayList<Integer>> x, int u,int v){
        
        int i=0, j, k = 0;  
        
       
        while(i < x.size()){ //Localizando vértice v
           j=0;
           while(j < x.get(i).size()) {               
               if (x.get(i).get(j) == v){                  
                   k = 1;
                   break;
               }
               j++;
           }
           if (k==1)
               break;
           i++;
        }      
       
        
        j=0;
        
        while(j < x.get(i).size()) { 
             if (x.get(i).get(j) == u)                  
               return false;        //Encontrou o u no mesmo conjunto de v (Ciclo)         
             j++;
         }     
         
        
        return true;
    
    }
    
    public ArrayList<ArrayList<Integer>> uniãoConjunto(ArrayList<ArrayList<Integer>> x, int u, int v){
        int i=0, j=0, k = 0, l=0;     
        
        while(i < x.size()){ //Localizando vértice v
           j=0;
           while(j < x.get(i).size()) {
               
               if (x.get(i).get(j) == v){
                   k = 1;
                   break;
               }
               j++;
           }
           if (k==1)
               break;
           i++;
        }
         
        l = i;// Armazena lugar do v 
        
        k=i=0;
       while(i < x.size()){ //Localizando vértice u
           j=0;
           while(j < x.get(i).size()) {
               if (x.get(i).get(j) == u){
                   k = 1;
                   break;
               }
               j++;
           }
           if (k==1)
               break;
           i++;
        }
        
        while (!x.get(i).isEmpty()){
             x.get(l).add(x.get(i).get(0));            
             x.get(i).remove(0);           
        }     
       
        
    return x;
    }
    
    public ArrayList criarConjunto (ArrayList<ArrayList<Integer>> v){
        int i;      
        for(i=0;i < grafo.numVert ;i++){            
            ArrayList<Integer> a = new ArrayList();
            a.add(i);            
            v.add(a);
        }       
    return v;
    }
    
    public void Dijkstra(int s){
        ArrayList<Q> a = new ArrayList();        
        ArrayList<Integer> adjacentes = new ArrayList(); int i,u;
        
        Inicializa(a,pi,s); 
        
            
        
        while(!vazioQ(a)){
            u = a.indexOf(extrairMinimo(a));            
            
            for (i=0; i < grafo.numVert; i++){  //Pega os vértices adjacentes de u
               if (grafo.verificarAdjacencia(u, i)){
                    adjacentes.add(i);
               }
            }           
                      
            while(!adjacentes.isEmpty()){    //Verifica todos os vértices adjacentes de u          
                a = relaxa(a,u,adjacentes.get(0));
                adjacentes.remove(0);
            }         
         
        }

          NovoJFrame.jTextArea1.append("\n");
          NovoJFrame.jTextArea1.append("\nCaminho mínimo, utilizando o algoritmo de Dijkstra, com início no vértice["+s+"]\n\n");
          
          for(i=0; i<grafo.numVert;i++){
                NovoJFrame.jTextArea1.append("Vertice["+i+"]:"+" Predecessor(Pai): "+pi.get(i)+"      distância: "+ a.get(i).chave );
                NovoJFrame.jTextArea1.append("\n");   
           }

            d.clear(); //Limpando os ArrayLists para usar de novo na Busca com outro vértice.
            f.clear();                                                 
            l_vert.clear();                              
            cor.clear(); 
            pi.clear();  
        
    }
    
    public ArrayList<Q> Inicializa (ArrayList<Q> Q, ArrayList<Integer> pi,int s){
        int i = 0;       
       
        for (; i < grafo.numVert; i++){
            pi.add(i, -1);             
            Q.add(new Q(true,Integer.MAX_VALUE));
        }        
        Q.get(s).chave = 0;       
         
    return Q;
    }
    
    public ArrayList<Q> relaxa (ArrayList<Q> Q, int u, int v){
       
         if(Q.get(v).chave > Q.get(u).chave + grafo.valorPeso(u, v)){
          Q.get(v).chave = Q.get(u).chave + grafo.valorPeso(u, v);
          pi.set(v, u);
      }
     return Q;
     }    
    
    public boolean Bellman_Ford(int s){
        ArrayList<Q> a = new ArrayList();        
        ArrayList<A> arestas = new ArrayList(); int i,j;
        
        Inicializa(a,pi,s); 
        
        for(i=0; i < grafo.numVert ;i++){ //Adiciona as arestas
            for(j=0; j < grafo.numVert ;j++){
                if(grafo.verificarAdjacencia(i, j))                                      
                    arestas.add(new A(i,j, grafo.valorPeso(i, j)));                    
                }                   
        }        
      
        
         
         for(i = 0; i < arestas.size() ;i++){ //Remove Arestas Repetidas de A_Linha (Grafo A->B == B->A)
             for(j=1; j < arestas.size(); j++){                  
                    if (arestas.get(i).u == arestas.get(j).v && arestas.get(i).v == arestas.get(j).u)
                      arestas.remove(j);
               }
         }       
            
        
        for(j=1; j <= grafo.numVert-1; j++){ // Para cada aresta 
              for(i=0; i < arestas.size(); i++)
                  relaxa(a,arestas.get(i).u,arestas.get(i).v);              
        }
        
       
         for(i=0; i < arestas.size(); i++) {
             if(a.get(arestas.get(i).v).chave >  a.get(arestas.get(i).u).chave + grafo.valorPeso(arestas.get(i).u, arestas.get(i).v) )
                 return false;
         }
        
          NovoJFrame.jTextArea1.append("\n");
          NovoJFrame.jTextArea1.append("\nCaminho mínimo, utilizando o algoritmo de Bellman-Ford, com início no vértice["+s+"]\n\n");
          
          for(i=0; i<grafo.numVert;i++){
                NovoJFrame.jTextArea1.append("Vertice["+i+"]:"+" Predecessor(Pai): "+pi.get(i)+"      distância: "+ a.get(i).chave );
                NovoJFrame.jTextArea1.append("\n");   
           }

            d.clear(); //Limpando os ArrayLists para usar de novo na Busca com outro vértice.
            f.clear();                                                 
            l_vert.clear();                              
            cor.clear(); 
            pi.clear();  
    return true;
    }
    
    public boolean caminhoDoisVertices(int u, int v, int opcao){
        
    if (opcao == 1){
        
    
    }    
    else{
        
    
    }   
    return false;
    }
    
    public boolean conexo(){
    
    return false;
    }
    
    public void mostraCaminho(int u, int v){
    
        if (v == u){
            NovoJFrame.jTextArea1.append("\n");
            NovoJFrame.jTextArea1.append("Aresta: "+u+" "+pi.get(v));      
        }
        else if (pi.get(v) == -1)
            NovoJFrame.jTextArea1.append("\nNão há caminho entre o vértice["+u+"] e o vértice["+v+"]");
         else
            mostraCaminho(u,pi.get(v));
          NovoJFrame.jTextArea1.append("\nAresta: "+v); 
        
    
    }
}
