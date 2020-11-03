/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Vinicius
 */
//Classe utilizada para colocar pesos nas ligações entre os neurônios das camadas 
public class Peso {
    
    private String neuronio, peso, ValorPeso;//origem, destino e valor 
 
    public Peso() {
    }

    public String getValorPeso() {
        return ValorPeso;
    }

    public void setValorPeso(String ValorPeso) {
        this.ValorPeso = ValorPeso;
    }

    public String getNeuronio() {
        return neuronio;
    }

    public void setNeuronio(String neuronio) {
        this.neuronio = neuronio;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    
}
