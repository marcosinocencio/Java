
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Vinicius
 */
public class Entrada {
     List<String> listaEntradas = new ArrayList<String>(); //Lista que armazenará a lista de entradas

    //Construtor
    public Entrada(String caminhoArquivo) {
        separarEntradas(caminhoArquivo);
    }

    
    private void separarEntradas(String caminhoArquivo) {
        String temporario;
        try {
            
            BufferedReader linhaDoArquivo; 

            
            linhaDoArquivo = new BufferedReader(new FileReader(caminhoArquivo));

            //efetua a leitura da primeira linha do arquivo, pois na primeira, estão apenas os nomes das entradas
            temporario = linhaDoArquivo.readLine(); 
            
            //while efetua a leitura linha a linha do arquivo
            while((temporario=linhaDoArquivo.readLine())!= null){//lê a linha do arquivo e atribui a temporario
                listaEntradas.add(temporario); //adiciona a linha lida a lista de entradas
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Entrada.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Entrada.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //retorna a linha de entradas
    public List getListaEntradas(){
        return listaEntradas;
    }

}
