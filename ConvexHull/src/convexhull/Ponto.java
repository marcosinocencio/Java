/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package convexhull;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Danilo Medeiros Eler
 */
public class Ponto implements Comparable<Ponto>{

    private int x;
    private int y;
    private Color cor = Color.RED;
    private int raio = 5;

    public Ponto(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(BufferedImage image) {
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        g2.setColor(this.cor);
        g2.fillOval(((int) this.x) - this.getRaio(), ((int) this.y)
                - this.getRaio(), this.getRaio() * 2, this.getRaio() * 2);
    }

    public int getRaio(){
        return this.raio;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int compareTo(Ponto p) {
        if (this.x < p.x)
            return -1;
        else if (this.x > p.x){
            return 1;
        }else return 0;
    }

}
