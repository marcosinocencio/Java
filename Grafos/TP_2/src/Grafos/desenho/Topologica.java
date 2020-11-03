/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grafos.desenho;

import Grafos.desenho.color.RainbowScale;
import java.util.ArrayList;

/**
 *
 * @author Vinicius
 */
public class Topologica {
    
    protected ArrayList<Vertex> vertex = new ArrayList<>();
    protected ArrayList<EdgeT> edges = new ArrayList<>();
    
    
     public Topologica(int nVert, ArrayList<Integer> vetor) {
        RainbowScale cS = new RainbowScale();
        //GrayScale cS = new GrayScale();
        int colorStep = 255 / nVert;
            for (int i=0; i<nVert; i++){
                Vertex v = new Vertex();
                v.setID(vetor.get(i));
                v.setColor(cS.getColor(i*colorStep));
                this.vertex.add(v);
            } 
            calcularPosicao();
        }
     
     public void calcularPosicao(){
        int nVert = this.vertex.size();
        int X = 30;
        int Y = 200;
        int desl = 80;
        for (int i=0; i<nVert; i++){               
                this.vertex.get(i).setX(X);
                this.vertex.get(i).setY(Y); 
                X += desl;
        }
    }
     
     public void draw(java.awt.Graphics2D g2) {
       //Draw each edges of the graph
        for (EdgeT edge : edges) {
            edge.draw(g2);
        }
        //Draw each vertice of the graph
        for (Vertex v : this.vertex) {
            v.draw(g2);
        }
    }
     
     public ArrayList<Vertex> getVertex() {
        return this.vertex;
    }
     
     public void addVertex(Vertex v){
        this.vertex.add(v);
     }

     public void addEdgeT(EdgeT e){
        this.edges.add(e);
     }   
     
}
