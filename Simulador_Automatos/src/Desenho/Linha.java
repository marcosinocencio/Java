/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Desenho;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;

/**
 *
 * @author Vinicius
 */
public class Linha extends javax.swing.JPanel{
    private Shape line;
    
    public Linha (Shape line, Dimension d){
        this.line = line;
        setBackground(Color.WHITE);
        setSize(d);
    }
    
    @Override
     protected void paintComponent( Graphics g ) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
	        g2d.setPaint(Color.GRAY);
	        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        g2d.draw(line);           
         
    }
}
