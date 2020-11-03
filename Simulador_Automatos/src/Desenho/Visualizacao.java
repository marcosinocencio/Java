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
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import simulador.Estado;
import simulador.Transicao;
import simulador.Utils;

/**
 *
 * @author Vinicius
 */
public class Visualizacao extends javax.swing.JPanel {
    private ArrayList< Estado > estados;
    /**
     * Creates new form Visualizacao
     */
    public Visualizacao(ArrayList<Estado> estados) {
        initComponents();
        setBackground( Color.WHITE );
        this.estados = estados;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    protected void paintComponent( Graphics g ) {
       
        super.paintComponent( g );      
              
            
        Graphics2D g2d = ( Graphics2D ) g;
        g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON );           
      
        
        g2d.setPaint( Color.BLACK );
        
        for ( Estado e : estados ) {         

            for ( Transicao t : e.getTransicoes() ) {
                
                // se estado origem e destino são diferentes, traça a reta
                if ( !t.getEstadoDestino().equals( e ) ) {
                    
                    //Váriaveis usadas para desenhar a flecha
                    // gera o grau relativo entre os estados
                    double gr = Utils.obtemGrauRelativoJava(
                            e.getXCentral(), e.getYCentral(),
                            t.getEstadoDestino().getXCentral(),
                            t.getEstadoDestino().getYCentral() );
                    
                    // gera a hipotenusa
                    double h = Utils.gerarHipotenusa(
                            e.getXCentral(), e.getYCentral(),
                            t.getEstadoDestino().getXCentral(),
                            t.getEstadoDestino().getYCentral() );
                    
                    double x = ( h - 25 ) * Math.cos( Math.toRadians( gr ) );
                    double cy = ( h - 25 ) * Math.sin( Math.toRadians( gr ) );
                    
                    Graphics2D g2df;
                    
                    
                    if(!verificaTransicao(e,t)){ // Se não há transição de ida e volta entre dois estados
                        // desenha a linha de ligação
                        g2d.draw( new Line2D.Double(
                                e.getXCentral(), e.getYCentral(), 
                                t.getEstadoDestino().getXCentral(), 
                                t.getEstadoDestino().getYCentral() ) );    

                        g2d.setFont(new Font("Arial", Font.PLAIN, 16)); 
                        int y=0;
                        for(String s : t.getSimbolo()){
                            g2d.drawString(s, (e.getXCentral()+t.getEstadoDestino().getXCentral())/2, e.getYCentral() - 10 + ( ( t.getEstadoDestino().getYCentral() - e.getYCentral() ) / 2 )-y);
                            y += 20;
                        }
                        
                             
                        g2df = ( Graphics2D ) g2d.create();

                        // faz a translação para a coordenada que deve ser a origem
                        g2df.translate( x + e.getXCentral(), cy + e.getYCentral());

                        // rotaciona
                        g2df.rotate( Math.toRadians( gr ) );

                        // desenha a flecha
                        g2df.draw( new Line2D.Double( -6, 0, -15, -10 ) );
                        g2df.draw( new Line2D.Double( -6, 0, -15, 10 ) );

                        // libera o graphics, não sendo necessário voltar a translação
                        // nem a rotação
                        g2df.dispose();   
                     }                   
                    else { //Há transição de ida e volta entre dois estados
                        int px, py;
                        g2df = ( Graphics2D ) g2d.create();                   
                        g2df.translate(e.getXCentral(), e.getYCentral());
                        px = e.getXCentral(); 
                        py = e.getYCentral(); 
                        g2df.rotate( Math.toRadians( gr ) );
                    
                        g2df.draw(new Arc2D.Double(29, -30,
                             Math.sqrt( Math.pow( t.getEstadoDestino().getXCentral()-px,2) + Math.pow(t.getEstadoDestino().getYCentral()-py,2 ) )-58,
                             50,
                             0, 180,
                             Arc2D.OPEN));
                        
                        g2df.setFont(new Font("Arial", Font.PLAIN, 16)); 
                        
                        int y=40,d;
                        d = (int)Math.sqrt( Math.pow( t.getEstadoDestino().getXCentral()-px,2) + Math.pow(t.getEstadoDestino().getYCentral()-py,2 ) );
                       
                        Graphics2D g2df3 = ( Graphics2D ) g2d.create(); 
                        g2df3.setFont(new Font("Arial", Font.PLAIN, 16)); 
                        g2df3.translate(e.getXCentral(), e.getYCentral());
                        px = e.getXCentral(); 
                        py = e.getYCentral(); 
                        g2df3.rotate( Math.toRadians( gr ) );
                        
                        int entrou = 0;  
                        for(String s : t.getSimbolo()){
                            if(e.getXCentral() < t.getEstadoDestino().getXCentral())
                                g2df3.drawString(s, d/2,  -y);
                            else {
                                if(entrou == 0 ){                                    
                                    g2df3.rotate(Math.toRadians( 180 ));
                                    entrou++;
                                }
                                g2df3.drawString(s, -d/2,  y+10);
                            }
                            y += 20;
                        }
                       
                        px = (int)Math.sqrt( Math.pow( t.getEstadoDestino().getXCentral()-px,2) + Math.pow(t.getEstadoDestino().getYCentral()-py,2 ) )+15;
                        py = 18;
                        
                        Graphics2D g2df2;  
                        g2df2 = g2df;
                        g2df2.translate(px, py);
                        g2df2.rotate( Math.toRadians( 30 ) );
                        g2df2.draw( new Line2D.Double( -50, 0, -59, 10 ) );
                        g2df2.draw( new Line2D.Double( -50, 0, -59, -10 ) );
                        
                        g2df.dispose();
                        g2df2.dispose();
                        g2df3.dispose();
                    }                  
                   
                } 
                else { // caso contrário, desenha arco
                    
                    g2d.draw( new Arc2D.Double(e.getXCentral()-15, e.getYCentral()-70, 30, 90,
                         0, 180,
                         Arc2D.OPEN));
                    
                    g2d.draw( new Line2D.Double(
                            e.getXCentral() - 15, e.getYCentral() - 25, 
                            e.getXCentral() - 25, e.getYCentral() - 40 ) );
                    
                    g2d.draw( new Line2D.Double(
                            e.getXCentral() - 15, e.getYCentral() - 25, 
                            e.getXCentral() - 5, e.getYCentral() - 40 ) );                   
                  
                    g2d.setFont(new Font("Arial", Font.PLAIN, 16)); 
                    
                    int y=0;
                    for(String s : t.getSimbolo()){
                        g2d.drawString(s, e.getXCentral()-5, e.getYCentral()-75-y);
                        y += 20;
                    }
                    
                }
                
            } // for transições 
            
            if ( e.isInicial() ) {    //Estado inicial          
                g2d.draw( new Line2D.Double(e.getXCentral()-30, e.getYCentral(), e.getXCentral()-60, e.getYCentral()-30));
                g2d.draw( new Line2D.Double(e.getXCentral()-60, e.getYCentral()-30, e.getXCentral()-60, e.getYCentral()+30));
                g2d.draw( new Line2D.Double(e.getXCentral()-60, e.getYCentral()+30, e.getXCentral()-30, e.getYCentral()));
                g2d.setPaint(Color.WHITE);  
                g2d.fillPolygon(new int[] {e.getXCentral()-31, e.getXCentral()-59, e.getXCentral()-59}, new int[] {e.getYCentral(), e.getYCentral()-29, e.getYCentral()+29,}, 3);
                g2d.setPaint(Color.BLACK);                
            }
            
        }//for estados 
         
    }
    
    private boolean verificaTransicao (Estado estado, Transicao t){     
       

        for ( Transicao a : t.getEstadoDestino().getTransicoes() ) {
            if ( a.getEstadoDestino().equals(estado)  ) 
                return true;
            
        }
        
        return false;
    }
    
    public void setEstados( ArrayList<Estado> estados ) {
        this.estados = estados;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
