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
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import simulador.Estado;
import simulador.FramePrincipalMealy;
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
public class DesenhoEstadoMealy extends javax.swing.JPanel implements Serializable{
     /**
     * Creates new form Teste
     */     
    private Estado estado;
    private int flecha;  
    private boolean clicado;
    private transient FramePrincipalMealy framePrincipalMealy;
    private int x,y;  
    private Dimension area;  
    private Shape line;
    private DesenhoEstadoMealy aux;
    
    public DesenhoEstadoMealy(Estado estado, FramePrincipalMealy framePrincipalMealy){        
        
        
        setSize(61, 61);
        
        area = new Dimension(0,0);
        this.framePrincipalMealy = framePrincipalMealy;
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
         
        if(framePrincipalMealy.editar.isSelected() && SwingUtilities.isLeftMouseButton(evt) && getParent().getMousePosition()!=null){            
          
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
                  framePrincipalMealy.jPanel1.repaint();          
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
                    framePrincipalMealy.jPanel1.setPreferredSize(area);

                    //Let the scroll pane know to update itself
                    //and its scrollbars.
                    framePrincipalMealy.jPanel1.revalidate();
                }


              x += dx;
              y += dy;
          
        
       }
        
          if(framePrincipalMealy.transicao.isSelected()){
            if(SwingUtilities.isLeftMouseButton(evt)){
                Line2D shape =(Line2D)line;
                if(getParent().getMousePosition()!=null)
                    shape.setLine(shape.getP1(), getParent().getMousePosition());
                framePrincipalMealy.jPanel1.repaint();             
            } 
        }
    }                                 

    private void formMousePressed(java.awt.event.MouseEvent evt) {                                
           
        if(framePrincipalMealy.editar.isSelected() && evt.getButton() == 1){
            clicado = true;
            getParent().repaint();
            repaint(); 
            x = getParent().getMousePosition().x;
            y = getParent().getMousePosition().y;
        }
        if(framePrincipalMealy.editar.isSelected()){
            framePrincipalMealy.estadoSelecionado = this;
            checkPopup(evt);
        }
        
        if(framePrincipalMealy.transicao.isSelected()){
            if(evt.getButton() == 1){
                line = new Line2D.Double(getParent().getMousePosition(), getParent().getMousePosition());
                Linha a = new Linha(line,framePrincipalMealy.jPanel1.getSize());  
                framePrincipalMealy.jPanel1.add(a);
                framePrincipalMealy.jPanel1.repaint();
                aux = this;
            } 
        }
        
        if(framePrincipalMealy.excluir.isSelected()){
            if(evt.getButton() == 1){
                framePrincipalMealy.setEnabled(false);
                framePrincipalMealy.ExcluirTransicoes.setVisible(true);
                framePrincipalMealy.ExcluirTransicoes.setLocationRelativeTo(null);
                framePrincipalMealy.estadoSelecionadoLabel.setText(estado.getNome());
                DefaultComboBoxModel modelo = new DefaultComboBoxModel();
                framePrincipalMealy.estadoSelecionado = this;
                for ( Transicao e : estado.getTransicoes() )                     
                     modelo.addElement( e.getEstadoDestino().getNome() );
                
                framePrincipalMealy.estadoDestinoComboBox.setModel( modelo );
                framePrincipalMealy.estadoDestinoComboBox.setSelectedIndex(-1);
            }
        }
        
    }                                 

    private void formMouseReleased(java.awt.event.MouseEvent evt) {    
        clicado = false;
        getParent().repaint();
        repaint();
        
        if (framePrincipalMealy.editar.isSelected()){
            framePrincipalMealy.estadoSelecionado = this;
            checkPopup(evt);
        }
        
         if(framePrincipalMealy.transicao.isSelected()){
            if(evt.getButton() == 1){
                framePrincipalMealy.jPanel1.remove(framePrincipalMealy.jPanel1.getComponentCount()-1);
                framePrincipalMealy.jPanel1.repaint();
                
                JTextField simbolo = new JTextField();
                JTextField saida = new JTextField();
                Object[] message = {
                    "Símbolo:", simbolo,
                    "Saída:", saida
                };
               
                int i = JOptionPane.showConfirmDialog(framePrincipalMealy,
                message,"Transição", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE,null);
                
                if (i != 2 && i != -1){
                       
                        boolean transicaoExiste = false;
                        Transicao tExistente = null;

                        for ( Transicao t : estado.getTransicoes() ) {
                            if ( t.getEstadoDestino().equals(framePrincipalMealy.estadoSelecionado.estado)  ) {
                                transicaoExiste = true;
                                tExistente = t;
                                break;
                            }
                        }

                        if(!transicaoExiste){
                            TreeSet<String> set = new TreeSet();
                            if (simbolo.getText().equals("") ){
                                if(saida.getText().equals(""))
                                     set.add("λ ; λ");
                                else 
                                    set.add("λ ; "+saida.getText());
                                this.estado.getTransicoes().add(new Transicao(set,framePrincipalMealy.estadoSelecionado.estado));
                            }
                            else {
                                if(saida.getText().equals(""))
                                    set.add(simbolo.getText()+" ; λ");
                                else
                                    set.add(simbolo.getText()+" ; "+saida.getText());
                                this.estado.getTransicoes().add(new Transicao(set,framePrincipalMealy.estadoSelecionado.estado));
                            }               
                        }
                        else {

                            if (simbolo.getText().equals("") )   {                     
                                if(saida.getText().equals(""))                                
                                    tExistente.getSimbolo().add("λ ; λ");
                                else
                                    tExistente.getSimbolo().add("λ ; "+saida.getText()); 
                            }
                            else  { 
                                if(saida.getText().equals(""))
                                    tExistente.getSimbolo().add(simbolo.getText()+" ; λ");  
                                else 
                                    tExistente.getSimbolo().add(simbolo.getText()+" ; "+saida.getText());                          
                            }

                        }
                       framePrincipalMealy.jPanel1.repaint();

                }
            }
        }
    } 
    
    public void formMouseEntered(java.awt.event.MouseEvent evt) {
          if(framePrincipalMealy.excluir.isSelected())
            framePrincipalMealy.jPanel1.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon(getClass().getResource("/icones/x.png")).getImage(),new Point(10,10),"custom cursor"));      
           
          if(framePrincipalMealy.transicao.isSelected())
             framePrincipalMealy.estadoSelecionado = (DesenhoEstadoMealy)evt.getComponent();    
           
    }
    
    private void checkPopup(MouseEvent evt) {
      if (evt.isPopupTrigger()) {
        framePrincipalMealy.popup_menu.show(this, evt.getX(), evt.getY());
                       
        if(estado.isInicial()) 
            framePrincipalMealy.menu_inicial.setSelected(true);
        else 
           framePrincipalMealy.menu_inicial.setSelected(false);
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
