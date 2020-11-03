/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Grafos;

/**
 *
 * @author Danilo Medeiros Eler
 */
public class Coloracao {
    public void execute(Grafo grafo) {
        Representacao rep = grafo.getRepresentacao();
        cores = new int[rep.getNumVertices()];
        for (int i=0; i<cores.length; i++){
            cores[i] = -1;
        }
        int vMaiorGrau = verticeMaiorGrau(rep);
        System.out.println("Vertice de Maior Grau: " + vMaiorGrau);
        coloreVertice(rep, vMaiorGrau);
    }

    private void coloreVertice(Representacao rep, int vert) {
        cores[vert] = corApropriada(rep, vert);
        No adj = ((ListaAdjacencia) rep).getAdjacentes(vert);
        while (adj != null) {
            if (cores[adj.getVertID()] == -1) {
                coloreVertice(rep, adj.getVertID());
            }
            adj = adj.getProx();
        }
    }
    
    private int corApropriada(Representacao rep, int vert){
        int cor = -1;
        Boolean flag = false;        
        while (!flag){
            cor++;
            No adj = ((ListaAdjacencia) rep).getAdjacentes(vert);
            while (adj!=null && cores[adj.getVertID()] != cor){
                adj = adj.getProx();
            }
            if (adj == null){
                flag = true;
            }            
        }        
        return cor;
    }

    private int verticeMaiorGrau(Representacao rep){
        int vert = 0;
        int maior = Integer.MIN_VALUE;
        for (int i=0; i<rep.getNumVertices(); i++){
            int cont = 0;
            No aux = ((ListaAdjacencia) rep).getAdjacentes(i);
            while (aux != null){
                cont++;
                aux = aux.getProx();
            }
            if (cont > maior){
                maior = cont;
                vert = i;
            }
        }
        return vert;
    }

    public int getNumCores(){
        int numComp = 0;
        int count[] = new int[cores.length];
        for (int i=0; i< count.length; i++){
            count[i] = 0;
        }

        for (int i=0; i<cores.length; i++){
            if (count[cores[i]] == 0){
                count[cores[i]]++;
            }
        }

        for (int i=0; i<count.length; i++){
            numComp = numComp + count[i];
        }

        return numComp;
    }

    public int[] getCores() {
        return cores;
    }

    private int cores[] = null;
}