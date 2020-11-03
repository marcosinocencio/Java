/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Desenho;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
/**
 *
 * @author Vinicius
 */
public class DesenhoFita extends javax.swing.JPanel{
    String[] fita;
    int posicao;
    
    public DesenhoFita(String[] fita, int posicao){
      this.fita = fita;
      this.posicao = posicao;
    }
    
    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    public void setFita(String[] fita, int posicao) {
        this.fita = fita;
        this.posicao = posicao;
    }    
    
    
    @Override
     protected void paintComponent( Graphics g ) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
	        g2d.setPaint(Color.BLACK);
	        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        g2d.drawRect(0, 0, 155, 25);
                g2d.setPaint(Color.WHITE);
                g2d.fillRect(1,1, 154, 24);
                g2d.setPaint(Color.RED);
                g2d.fillRect(67,1, 20, 24);
                g2d.setPaint(Color.BLACK);
                g.setFont(new Font("Tahoma", Font.PLAIN, 20)); 
                int j = 3;
                for(int i = posicao; i < posicao+10; i++){
                    g2d.drawString(fita[i].toString(), j, 20);
                    j += 17;
                }   
         
    }
}
