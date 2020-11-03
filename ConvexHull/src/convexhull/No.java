/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package convexhull;

/**
 *
 * @author Danilo Medeiros Eler
 */
public class No {
    private Ponto p;
    public No prox;
    public No ant;
    public No(Ponto p){
        this.p = p;
        prox = null;
        ant = null;
    }
    public Ponto getP(){
        return this.p;
    }
}
