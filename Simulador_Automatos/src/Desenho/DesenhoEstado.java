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
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.io.Serializable;
import java.util.TreeSet;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import simulador.Estado;
import simulador.FramePrincipal;
import simulador.Transicao;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Vinicius
 */
public class DesenhoEstado extends javax.swing.JPanel implements Serializable{
     /**
     * Creates new form Teste
     */     
    private Estado estado;
    private int flecha;  
    private boolean clicado;
    private transient FramePrincipal framePrincipal;
    private int x,y;  
    private Dimension area;  
    private Shape line;
    private DesenhoEstado aux;
    
    public DesenhoEstado(Estado estado, FramePrincipal framePrincipal){        
        
        
        setSize(61, 61);
        
        area = new Dimension(0,0);
        this.framePrincipal = framePrincipal;
        this.setBackground(new Color(0,0,0,0));
        this.estado = estado;
        this.clicado = clicado;       
      
        
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }            
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                formMouseEntered(evt);
            }
        });
        
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
    }
   
    @Override
    protected void paintComponent( Graphics g ) {
            
            super.paintComponent( g );           
              
            
            Graphics2D g2d = ( Graphics2D ) g;
            g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, 
                    RenderingHints.VALUE_ANTIALIAS_ON );   
            if(clicado)
                g2d.setPaint( new Color( 200, 245, 255 ) );
            else
                g2d.setPaint(Color.YELLOW);
            
            g2d.fill( new Ellipse2D.Double(0 + flecha,0,60,60) );
            g2d.setPaint(Color.BLACK);           
            g2d.draw( new Ellipse2D.Double(0 + flecha,0,60,60) );   
            
                        
            if ( estado.isFinal() ) 
                g2d.draw( new Ellipse2D.Double(5 + flecha,5,50,50) );
             
           
            g2d.drawString(estado.getNome(), 23 + flecha, 35);
         
    }
    
    public Estado getEstado() {
        return estado;
    }
    
    public void setEstado(Estado estado){
        this.estado = estado;
    }
    
    private void formMouseDragged(java.awt.event.MouseEvent evt) {                                  
         
        if(framePrincipal.editar.isSelected() && SwingUtilities.isLeftMouseButton(evt) && getParent().getMousePosition()!=null){            
          
                int dx = getParent().getMousePosition().x - x;
                int dy = getParent().getMousePosition().y - y;            
                boolean changed = false; 


                  setLocation(x-30, y-30);                    
                  if (getX() < 0 && getY() < 0){            
                   setLocation(0, 0); 
                  }
                  else if(getX() < 0)
                      setLocation(0, getY());
                  else if(getY() < 0)
                     setLocation(getX(), 0);
                  framePrincipal.jPanel1.repaint();          
                  this.estado.setXCentral(x);
                  this.estado.setYCentral(y);
                    
                    int this_width = (x + 32);
                    if (this_width > area.width) {
                        area.width = this_width; changed=true;
                    }

                    int this_height = (y + 32);
                    if (this_height > area.height) {
                        area.height = this_height; changed=true;
                    }

                    if (this_width < area.width){
                        area.width = 0; changed=true;
                    }
                    if (this_height < area.height){
                        area.height = 0; changed=true;
                    }    
                  if (changed) {
                    //Update client's preferred size because
                    //the area taken up by the graphics has
                    //gotten larger or smaller (if cleared).
                    framePrincipal.jPanel1.setPreferredSize(area);

                    //Let the scroll pane know to update itself
                    //and its scrollbars.
                    framePrincipal.jPanel1.revalidate();
                }


              x += dx;
              y += dy;
          
        
       }
        
          if(framePrincipal.transicao.isSelected()){
            if(SwingUtilities.isLeftMouseButton(evt)){
                Line2D shape =(Line2D)line;
                if(getParent().getMousePosition()!=null)
                    shape.setLine(shape.getP1(), getParent().getMousePosition());
                framePrincipal.jPanel1.repaint();             
            } 
        }
    }                                 

    private void formMousePressed(java.awt.event.MouseEvent evt) {                                
           
        if(framePrincipal.editar.isSelected() && evt.getButton() == 1){
            clicado = true;
            getParent().repaint();
            repaint(); 
            x = getParent().getMousePosition().x;
            y = getParent().getMousePosition().y;
        }
        if(framePrincipal.editar.isSelected()){
            framePrincipal.estadoSelecionado = this;
            checkPopup(evt);
        }
        
        if(framePrincipal.transicao.isSelected()){
            if(evt.getButton() == 1){
                line = new Line2D.Double(getParent().getMousePosition(), getParent().getMousePosition());
                Linha a = new Linha(line,framePrincipal.jPanel1.getSize());  
                framePrincipal.jPanel1.add(a);
                framePrincipal.jPanel1.repaint();
                aux = this;
            } 
        }
        
        if(framePrincipal.excluir.isSelected()){
            if(evt.getButton() == 1){
                framePrincipal.setEnabled(false);
                framePrincipal.ExcluirTransicoes.setVisible(true);
                framePrincipal.ExcluirTransicoes.setLocationRelativeTo(null);
                framePrincipal.estadoSelecionadoLabel.setText(estado.getNome());
                DefaultComboBoxModel modelo = new DefaultComboBoxModel();
                framePrincipal.estadoSelecionado = this;
                for ( Transicao e : estado.getTransicoes() )                     
                     modelo.addElement( e.getEstadoDestino().getNome() );
                
                framePrincipal.estadoDestinoComboBox.setModel( modelo );
                framePrincipal.estadoDestinoComboBox.setSelectedIndex(-1);
            }
        }
        
    }                                 

    private void formMouseReleased(java.awt.event.MouseEvent evt) {    
        String s;
        clicado = false;
        getParent().repaint();
        repaint();
        
        if (framePrincipal.editar.isSelected()){
            framePrincipal.estadoSelecionado = this;
            checkPopup(evt);
        }
        
         if(framePrincipal.transicao.isSelected()){
            if(evt.getButton() == 1){
                framePrincipal.jPanel1.remove(framePrincipal.jPanel1.getComponentCount()-1);
                framePrincipal.jPanel1.repaint();
                
               
                s = JOptionPane.showInputDialog(framePrincipal,
                "Símbolo: ","Transição", JOptionPane.PLAIN_MESSAGE);
                
                if (s != null){
                       
                        boolean transicaoExiste = false;
                        Transicao tExistente = null;

                        for ( Transicao t : estado.getTransicoes() ) {
                            if ( t.getEstadoDestino().equals(framePrincipal.estadoSelecionado.estado)  ) {
                                transicaoExiste = true;
                                tExistente = t;
                                break;
                            }
                        }

                        if(!transicaoExiste){
                            TreeSet<String> set = new TreeSet();
                            if (s.equals("") ){
                                set.add("λ");
                                this.estado.getTransicoes().add(new Transicao(set,framePrincipal.estadoSelecionado.estado));
                            }
                            else {
                                set.add(s);
                                this.estado.getTransicoes().add(new Transicao(set,framePrincipal.estadoSelecionado.estado));
                            }               
                        }
                        else {

                            if (s.equals("") )                        
                                tExistente.getSimbolo().add("λ");

                            else                         
                                tExistente.getSimbolo().add(s);     

                        }
                       framePrincipal.jPanel1.repaint();

                }
            }
        }
    } 
    
    public void formMouseEntered(java.awt.event.MouseEvent evt) {
          if(framePrincipal.excluir.isSelected())
            framePrincipal.jPanel1.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon(getClass().getResource("/icones/x.png")).getImage(),new Point(10,10),"custom cursor"));      
           
          if(framePrincipal.transicao.isSelected())
             framePrincipal.estadoSelecionado = (DesenhoEstado)evt.getComponent();    
           
    }
    
    private void checkPopup(MouseEvent evt) {
      if (evt.isPopupTrigger()) {
        framePrincipal.popup_menu.show(this, evt.getX(), evt.getY());
        
        if(estado.isFinal())
            framePrincipal.menu_final.setSelected(true);          
        else 
            framePrincipal.menu_final.setSelected(false); 
        
        if(estado.isInicial()) 
            framePrincipal.menu_inicial.setSelected(true);
        else 
           framePrincipal.menu_inicial.setSelected(false);
      }
    }
     
   
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 396, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 296, Short.MAX_VALUE)
        );
    }// </editor-fold>                        


    // Variables declaration - do not modify                     
    // End of variables declaration                   
}
