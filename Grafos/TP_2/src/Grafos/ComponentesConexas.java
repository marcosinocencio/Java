/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Grafos;

/**
 *
 * @author Danilo Medeiros Eler
 */
public class ComponentesConexas {
    private int numComp;
    
    public void execute(Grafo grafo) {
        Representacao rep = grafo.getRepresentacao();
        componentes = new int[rep.getNumVertices()];
        buscaProfundidade(rep);

    }

    private void buscaProfundidade(Representacao rep) {
        for (int i = 0; i < componentes.length; i++) {
            componentes[i] = -1;
        }
        int idComp = -1;
        for (int i = 0; i < componentes.length; i++) {
            if (componentes[i] == -1) {
                idComp++;
                visita(rep, i, idComp);
            }
        }
        this.numComp = idComp+1;
    }

    private void visita(Representacao rep, int vert, int idComp) {
        componentes[vert] = idComp;
        No adj = ((ListaAdjacencia) rep).getAdjacentes(vert);
        while (adj != null) {
            if (componentes[adj.getVertID()] == -1) {
                visita(rep, adj.getVertID(), idComp);
            }
            adj = adj.getProx();
        }
    }

    public int getNumComponentes(){        
        return numComp;
    }

    public int[] getComponentes() {
        return componentes;
    }

    private int componentes[] = null;
}
