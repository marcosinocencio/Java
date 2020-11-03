/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Grafos;

/**
 *
 * @author Danilo Medeiros Eler
 */
public class MatrizAdjacencia extends Representacao {

    private int[][] matriz;

    @Override
    public void init(int numVertices) {
        numVert = numVertices;
        matriz = new int[numVertices][numVertices];
        fillMatrizAdjacencia(0);
    }

    public void fillMatrizAdjacencia(int value) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                matriz[i][j] = value;
            }
        }
    }
    
    @Override
    public void removeAresta(int vIni){
     
    }

    @Override
    public void addAresta(int vIni, int vFim, int tipo, int peso) {
        int vi = vIni;
        int vj = vFim;

        matriz[vi][vj] = 1;
        matriz[vj][vi] = 1;
    }

    @Override
    public void imprimeRepresentacao(String mensagem) {
        System.out.println("=================================");
        System.out.println(mensagem);
        System.out.println("=================================\n");
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print(matriz[i][j]+" ");
            }
            System.out.println("");
        }
    }
}
