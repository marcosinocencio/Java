
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Vinicius
 */
public class RNA {
    
    private int NeuroniosCE=0; // Quantidade de neurônios da camada de entrada 
    private int NeuroniosCO=0; // Quantidade de neurônios da camada oculta
    private int NeuroniosCS=0; // Quantidade de neurônios da camada de saída 
    
    public RNA (String arquivo){
        camadas(arquivo);
    }
    
    public int getNeuroniosCE() {
        return NeuroniosCE;
    }

    public int getNeuroniosCO() {
        return NeuroniosCO;
    }
    
     public int getNeuroniosCS() {
        return NeuroniosCS;
    }

    public void setNeuroniosCO(int NeuroniosCO) {
        this.NeuroniosCO = NeuroniosCO;
    }
   
    private void camadas(String caminhoArquivo){
        
        try {
        String vetorLinha [], linha; ///Varável que receberá os blocos da primeira linha do arquivo carregado
        
        
        BufferedReader linhaDoArquivo;
        
            linhaDoArquivo = new BufferedReader(new FileReader(caminhoArquivo));
        
        
            vetorLinha = linhaDoArquivo.readLine().split(","); 
            NeuroniosCE = vetorLinha.length - 1; //Ultima coluna da linha é a classe
            
            //lê uma linha por vez, até o final do arquivo
            while((linha = linhaDoArquivo.readLine())!= null){ 
                vetorLinha = linha.split(","); 
                if(Integer.parseInt(vetorLinha[NeuroniosCE]) > NeuroniosCS){
                    NeuroniosCS = Integer.parseInt(vetorLinha[NeuroniosCE]);
                }
            }
            
            //Determina a quantidade de Neuronios na Camada Oculta
           NeuroniosCO = (int) Math.sqrt((NeuroniosCE * NeuroniosCS));
            
        }catch (IOException ex) {
            Logger.getLogger(RNA.class.getName()).log(Level.SEVERE, null, ex);
        }          
    }
    
      //Gera os pesos aleatóriamente.
    public float geraPeso(){
        Random numero = new Random(); 
        
        float n_gerado = numero.nextFloat(); //Gera um numero aletório no intervalo de 0 a 1
        int pos_neg = numero.nextInt(10); //É gerado um numero aleatório 0 ou 1, se for 0 o numer retornado será negativo, 
                                                    //se for 1 0 numero retornado será positivo
        if(pos_neg > 5){ //if garante que quando a variavel pos_neg for 0, que o numero retornado seja negativo
            n_gerado*=(-1);
        }
        return n_gerado;
    }
    
}
