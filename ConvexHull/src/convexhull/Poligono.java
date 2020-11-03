/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package convexhull;

/**
 *
 * @author Danilo Medeiros Eler
 */
public class Poligono {
    public No ini;
    public No fim;
    public No maisDireita;
    public No maisEsquerda;
    public int size;
    public Poligono(){
        ini = null;
        fim = null;
        maisDireita = null;
        maisEsquerda = null;
        size = 0;
    }
    public void add(Ponto p){
        No no = new No(p);
        if (ini == null){            
            ini = no;
            no.prox = no;
            no.ant = no;
        }else{
            fim.prox = no;
            no.ant = fim;
            no.prox = ini;
            ini.ant = no;
        }
        fim = no;
        size++;
    }
}
