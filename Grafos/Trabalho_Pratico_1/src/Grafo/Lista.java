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
public class Lista {
    private Elem cabeca;
    public Lista(){
        this.cabeca = null;
    }
    public void add(int valor, int peso){
        Elem aux = new Elem(valor, peso);
        aux.setProx(cabeca);
        cabeca = aux;
    }
    
    public boolean contem(int valor){
        Elem aux = cabeca;
        while (aux != null){
            if (aux.getValor() == valor)
                return true;
            aux = aux.getProx();
        }
        return false;
    }
    
    public int Peso(int valor){
        Elem aux = cabeca;
        while (aux != null){
            if (aux.getValor() == valor)
                return aux.getPeso();
            aux = aux.getProx();
        } 
        return 0;
    }
    
    public void exibir(){
        Elem aux = cabeca;
        while (aux != null){
            NovoJFrame.jTextArea1.append("|"+aux.getValor()+","+aux.getPeso()+"|-->");
            aux = aux.getProx();
        }
        NovoJFrame.jTextArea1.append("null");
    }
}
