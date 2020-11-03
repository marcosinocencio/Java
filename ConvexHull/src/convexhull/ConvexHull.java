/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package convexhull;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Danilo Medeiros Eler
 */
public class ConvexHull {

    private ArrayList<Ponto> pontos;
    private Poligono convexHull;

    public ConvexHull(ArrayList<Ponto> pontos) {
        this.pontos = pontos;
    }

    public void execute() {

//      for (int i=0; i<5; i++){
//            convexHull.add(pontos.get((int)(Math.random()*(19))));
//        }
//      convexHull.add(convexHull.get(0));
        System.out.println("Execução em Progresso");
        Collections.sort(pontos);
        System.out.println("Ordenado por X");
        this.convexHull = convexHull(pontos, 0, pontos.size() - 1);
        System.out.println("Convex Hull Computado");
    }

    public Poligono merge(Poligono esq, Poligono dir) {
        ///precisa pensar nos casos em que muitos pontos consecutivos sao colineares
        // próxima tentativa: verificar looping em um dos poligonos (ori == 0)
        Poligono fecho = new Poligono();

        if (esq.size == 1 && dir.size == 1) //Cada um tem um ponto, monta a reta e retorna
        {
            fecho.add(esq.ini.getP());
            fecho.add(dir.ini.getP());
            fecho.maisEsquerda = fecho.ini;
            fecho.maisDireita = fecho.fim;
            return fecho;
        } else if (esq.size == 1 && dir.size == 2) //Monta um triangulo
        {
            if (orientacao(esq.ini.getP(), dir.ini.getP(), dir.ini.prox.getP()) <= 0) //Anti-Horario, basta juntar
            {
                fecho.add(esq.ini.getP());
                fecho.add(dir.ini.getP());
                fecho.add(dir.fim.getP());
                fecho.maisDireita = fecho.fim;
                fecho.maisEsquerda = fecho.ini;
            } else //Junta invertendo a direcao pra ficar antihorario
            {
                fecho.add(esq.ini.getP());
                fecho.add(dir.fim.getP());
                fecho.maisDireita = fecho.fim;
                fecho.add(dir.ini.getP());
                fecho.maisEsquerda = fecho.ini;
            }
        } else if (esq.size == 2 && dir.size == 1) //Monta um triangulo
        {
            if (orientacao(esq.ini.getP(), esq.ini.prox.getP(), dir.ini.getP()) <= 0) //Anti-Horario, basta juntar
            {
                fecho.add(esq.ini.getP());
                fecho.add(esq.ini.prox.getP());
                fecho.add(dir.ini.getP());
                fecho.maisDireita = fecho.fim;
                fecho.maisEsquerda = fecho.ini;
            } else //Junta invertendo a direcao pra ficar antihorario
            {
                fecho.add(esq.ini.getP());
                fecho.add(dir.ini.getP());
                fecho.maisDireita = fecho.fim;
                fecho.add(esq.ini.prox.getP());
                fecho.maisEsquerda = fecho.ini;
            }
        } else //Resto dos poligonos tratado genericamente
        {
            No aSup = esq.maisDireita; //inicializado com o ponto mais a direita do fecho da esquerda
            No bSup = dir.maisEsquerda; //inicializado com o ponto mais a esquerda do fecho da direita
            No aInf = esq.maisDireita; //inicializado com o ponto mais a direita do fecho da esquerda
            No bInf = dir.maisEsquerda; //inicializado com o ponto mais a esquerda do fecho da direita

            boolean continua = true;
            while (continua) //Enquanto nao encontrar a tangente inferior
            {
                continua = false;
                int ori = orientacao(aInf.getP(), aInf.ant.getP(), bInf.getP());
                if (ori <= 0) //Anti-horario
                {
                    aInf = aInf.ant;
                    continua = true;
                }
                ori = orientacao(aInf.getP(), bInf.getP(), bInf.prox.getP());
                if (ori >= 0) //Horario
                {
                    bInf = bInf.prox;
                    continua = true;
                }
            }

            continua = true;
            while (continua) //Enquanto nao encontrar a tangente superior
            {
                continua = false;
                int ori = orientacao(aSup.getP(), aSup.prox.getP(), bSup.getP());
                if (ori >= 0) //Horario
                {
                    aSup = aSup.prox;
                    continua = true;
                }
                ori = orientacao(aSup.getP(), bSup.getP(), bSup.ant.getP());
                if (ori <= 0) //Anti-Horario
                {
                    bSup = bSup.ant;
                    continua = true;
                }
            }

            //Religando os vertices pra formar as tangentes superior e inferior
            aSup.ant = bSup;
            bSup.prox = aSup;
            aInf.prox = bInf;
            bInf.ant = aInf;

            //Montando o fecho e retorna
            fecho.ini = aSup;
            fecho.maisDireita = dir.maisDireita;
            fecho.maisEsquerda = esq.maisEsquerda;
            fecho.size = 1;

            //Contando quantos pontos tem no novo fecho
            No aux = fecho.ini.prox;
            while (aux != null && aux != fecho.ini) {
                aux = aux.prox;
                (fecho.size)++;
            }
        }
        return fecho;
    }

    public Poligono convexHull(ArrayList<Ponto> pontos, int inicio, int fim) {
        if ((fim - inicio + 1) > 3) {
            int meio = (inicio + fim) / 2;
            Poligono esquerda = convexHull(pontos, inicio, meio);
            Poligono direita = convexHull(pontos, meio + 1, fim);
            return merge(esquerda, direita);
        } else {
            Poligono fecho = new Poligono();
            if ((fim - inicio + 1) == 3) {
                Ponto p1 = pontos.get(inicio);
                Ponto p2 = pontos.get(inicio + 1);
                Ponto p3 = pontos.get(inicio + 2);
                //armazena no sentido antihorario
                if (orientacao(p1, p2, p3) <= 0) {
                    fecho.add(p1);
                    fecho.add(p2);
                    fecho.add(p3);
                    fecho.maisDireita = fecho.fim;
                } else {
                    fecho.add(p1);
                    fecho.add(p3);
                    fecho.maisDireita = fecho.fim;
                    fecho.add(p2);
                }
            } else {
                for (int i = inicio; i <= fim; i++) {
                    fecho.add(pontos.get(i));
                }
                fecho.maisDireita = fecho.fim;
            }
            fecho.maisEsquerda = fecho.ini;
            return fecho;
        }
    }

    /****
     *
     * @param p1 ponto 1
     * @param p2 ponto 2
     * @param p3 ponto 3
     * @return resultado do determinante ==> Se  > 0 Anti-horario; Se < 0 Horario; Se 0 mesma linha
     */
    public int orientacao(Ponto p1, Ponto p2, Ponto p3) {
        return (p2.getX() * p3.getY() + p1.getX() * p2.getY() + p1.getY() * p3.getX()) - (p1.getY() * p2.getX() + p2.getY() * p3.getX() + p1.getX() * p3.getY());
    }

    public Poligono getConvexHullPoints() {
        return convexHull;
    }
}
