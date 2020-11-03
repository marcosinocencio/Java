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
public class Elem {
  private int valor, peso;
  private Elem prox;
  public Elem(int valor, int peso){
      this.valor = valor;
      this.peso = peso;
      this.prox = null;
  }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public Elem getProx() {
        return prox;
    }

    public void setProx(Elem prox) {
        this.prox = prox;
    }
}
