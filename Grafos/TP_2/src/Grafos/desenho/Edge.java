/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Grafos.desenho;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author Danilo Medeiros Eler
 */
public class Edge {

    private Color color = Color.WHITE; //Cor da aresta
    private Vertex source; //primeiro vetice da aresta
    private Vertex target; //segundo vertice da aresta
    private int peso, tipo; //peso da aresta
    private Boolean directed = false; //se a aresta é direcionada
    private Boolean selected = false; //se a aresta está selecionada

    public Edge(Vertex source, Vertex target, Boolean directed, int peso, int tipo) {
        this.source = source;
        this.target = target;
        this.peso = peso;
        this.tipo = tipo;
        if (directed == true)
            this.directed = true;      
    }

    public void draw(java.awt.Graphics2D g2) {
        //Combines the color of the two vertex to paint the edge

        if (selected) {
            g2.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, 1.0f));
            g2.setStroke(new java.awt.BasicStroke(3.0f));             
        } else {
            g2.setStroke(new java.awt.BasicStroke(1.0f));            
            if ((this.target.isSelected() && this.source.isSelected())) { //se os vertices estao selecionados
                g2.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, 0.5f));
            } else {//se os vertices nao estao selecionados
                g2.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, 0.2f));
            }
        }
        this.color = new Color((this.source.getColor().getRed() + this.target.getColor().getRed()) / 2,
                    (this.source.getColor().getGreen() + this.target.getColor().getGreen()) / 2,
                    (this.source.getColor().getBlue() + this.target.getColor().getBlue()) / 2);

        g2.setColor(this.color);
        
        if (this.tipo == 0){           

            g2.drawLine(((int) this.source.getX()), ((int) this.source.getY()),
                    ((int) this.target.getX()), ((int) this.target.getY()));
            g2.setStroke(new java.awt.BasicStroke(1.0f));

            g2.setFont(new Font("Verdana", Font.BOLD, 18));
            g2.setColor(color.BLACK);
            java.awt.FontMetrics metrics = g2.getFontMetrics(g2.getFont());
            g2.drawString(Integer.toString(peso),(this.source.getX()+this.target.getX())/2,(this.source.getY()+this.target.getY())/2);
        }
        else{
            this.color = color.RED;    
            g2.setColor(this.color);

            g2.drawLine(((int) this.source.getX()), ((int) this.source.getY()),
                    ((int) this.target.getX()), ((int) this.target.getY()));
            g2.setStroke(new java.awt.BasicStroke(1.0f));
            
            g2.setFont(new Font("Verdana", Font.BOLD, 18));
            g2.setColor(color.BLACK);
            java.awt.FontMetrics metrics = g2.getFontMetrics(g2.getFont());
            g2.drawString(Integer.toString(peso),(this.source.getX()+this.target.getX())/2,(this.source.getY()+this.target.getY())/2);
            
        }
        
        
        if (isDirected()) {
//            drawArrow(g2, new Point((int) source.getX(), (int) source.getY()),
//                    new Point((int) target.getX(), (int) target.getY()),
//                    6.0f);
         if(this.tipo == 0){
            this.color = new Color((this.source.getColor().getRed() + this.target.getColor().getRed()) / 2,
                    (this.source.getColor().getGreen() + this.target.getColor().getGreen()) / 2,
                    (this.source.getColor().getBlue() + this.target.getColor().getBlue()) / 2);

            g2.setColor(this.color);         
         }   
         else
             g2.setColor(color.RED);
         
         drawArrowNew(g2, new Point((int) source.getX(), (int) source.getY()),
                    new Point((int) target.getX(), (int) target.getY()),
                    9, 14);
        }


        g2.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, 1.0f));
    }
    
    private void drawArrowNew(Graphics2D g2, Point s, Point t, int size, int deslocamento) {
        float r = (float) Math.sqrt(Math.pow(s.x - t.x, 2) + Math.pow(s.y - t.y, 2));
        float cos = (t.x - s.x) / r;
        float sen = (t.y - s.y) / r;                
        
        int xAB = size + deslocamento;
        int yA = size;
        int yB = -size;
        
        Point pa = new Point(Math.round( xAB * -cos - yA * -sen )+t.x, Math.round( xAB * -sen + yA * -cos )+t.y);
        Point pb = new Point(Math.round( xAB * -cos - yB * -sen )+t.x, Math.round( xAB * -sen + yB * -cos )+t.y);
        Point pc = new Point(Math.round( deslocamento * -cos)+t.x, Math.round( deslocamento * -sen)+t.y);
        g2.setStroke(new java.awt.BasicStroke(2.0f)); 
        g2.drawLine(pc.x, pc.y, pa.x, pa.y);
        g2.drawLine(pc.x, pc.y, pb.x, pb.y);
    }

    private void drawArrow(Graphics2D g2, Point s, Point t, float size) {
        float r = (float) Math.sqrt(Math.pow(s.x - t.x, 2) + Math.pow(s.y - t.y, 2));
        float cos = (t.x - s.x) / (r);
        float sen = (t.y - s.y) / (r);

        //rotação e translação
        int transX = (int) ((t.x + s.x) * 0.5f); //metade da reta
        int transY = (int) ((t.y + s.y) * 0.5f); //metade da reta

        Point pa = new Point(Math.round(-sen * size) + (transX), Math.round(cos * size) + (transY));
        Point pb = new Point(Math.round(-sen * -size) + (transX), Math.round(cos * -size) + (transY));
        Point pc = new Point(Math.round(cos * size) + (transX), Math.round(sen * size) + (transY));

        g2.drawLine(pa.x, pa.y, pc.x, pc.y);
        g2.drawLine(pb.x, pb.y, pc.x, pc.y);

//        g2.setFont(new Font("Verdana", Font.BOLD, 100));
//        java.awt.FontMetrics metrics = g2.getFontMetrics(g2.getFont());
//        g2.drawString("T", pc.x, pc.y);
    }

    public Boolean isDirected() {
        return directed;
    }

    public void setDirected(Boolean directed) {
        this.directed = directed;
    }
    
    public void setSelected(Boolean selected){
        this.selected = selected;
    }
}
