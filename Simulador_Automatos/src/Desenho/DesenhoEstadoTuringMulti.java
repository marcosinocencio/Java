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
import simulador.FramePrincipalTuringMulti;
import simulador.PainelFitas;
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
public class DesenhoEstadoTuringMulti extends javax.swing.JPanel implements Serializable{
     /**
     * Creates new form Teste
     */     
    private Estado estado;
    private int flecha;  
    private boolean clicado;
    private transient FramePrincipalTuringMulti framePrincipalTuringMulti;
    private int x,y;  
    private Dimension area;  
    private Shape line;
    private DesenhoEstadoTuringMulti aux;
    private int nFitas;
    
    public DesenhoEstadoTuringMulti(Estado estado, FramePrincipalTuringMulti framePrincipalTuringMulti, int nFitas){        
        
        
        setSize(61, 61);
        
        area = new Dimension(0,0);
        this.framePrincipalTuringMulti = framePrincipalTuringMulti;
        this.setBackground(new Color(0,0,0,0));
        this.estado = estado;
        this.clicado = clicado;       
        this.nFitas = nFitas;
        
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
         
        if(framePrincipalTuringMulti.editar.isSelected() && SwingUtilities.isLeftMouseButton(evt) && getParent().getMousePosition()!=null){            
          
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
                  framePrincipalTuringMulti.jPanel1.repaint();          
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
                    framePrincipalTuringMulti.jPanel1.setPreferredSize(area);

                    //Let the scroll pane know to update itself
                    //and its scrollbars.
                    framePrincipalTuringMulti.jPanel1.revalidate();
                }


              x += dx;
              y += dy;
          
        
       }
        
          if(framePrincipalTuringMulti.transicao.isSelected()){
            if(SwingUtilities.isLeftMouseButton(evt)){
                Line2D shape =(Line2D)line;
                if(getParent().getMousePosition()!=null)
                    shape.setLine(shape.getP1(), getParent().getMousePosition());
                framePrincipalTuringMulti.jPanel1.repaint();             
            } 
        }
    }                                 

    private void formMousePressed(java.awt.event.MouseEvent evt) {                                
           
        if(framePrincipalTuringMulti.editar.isSelected() && evt.getButton() == 1){
            clicado = true;
            getParent().repaint();
            repaint(); 
            x = getParent().getMousePosition().x;
            y = getParent().getMousePosition().y;
        }
        if(framePrincipalTuringMulti.editar.isSelected()){
            framePrincipalTuringMulti.estadoSelecionado = this;
            checkPopup(evt);
        }
        
        if(framePrincipalTuringMulti.transicao.isSelected()){
            if(evt.getButton() == 1){
                line = new Line2D.Double(getParent().getMousePosition(), getParent().getMousePosition());
                Linha a = new Linha(line,framePrincipalTuringMulti.jPanel1.getSize());  
                framePrincipalTuringMulti.jPanel1.add(a);
                framePrincipalTuringMulti.jPanel1.repaint();
                aux = this;
            } 
        }
        
        if(framePrincipalTuringMulti.excluir.isSelected()){
            if(evt.getButton() == 1){
                framePrincipalTuringMulti.setEnabled(false);
                framePrincipalTuringMulti.ExcluirTransicoes.setVisible(true);
                framePrincipalTuringMulti.ExcluirTransicoes.setLocationRelativeTo(null);
                framePrincipalTuringMulti.estadoSelecionadoLabel.setText(estado.getNome());
                DefaultComboBoxModel modelo = new DefaultComboBoxModel();
                framePrincipalTuringMulti.estadoSelecionado = this;
                for ( Transicao e : estado.getTransicoes() )                     
                     modelo.addElement( e.getEstadoDestino().getNome() );
                
                framePrincipalTuringMulti.estadoDestinoComboBox.setModel( modelo );
                framePrincipalTuringMulti.estadoDestinoComboBox.setSelectedIndex(-1);
            }
        }
        
    }                                 

    private void formMouseReleased(java.awt.event.MouseEvent evt) {    
        
        clicado = false;
        getParent().repaint();
        repaint();
        
        if (framePrincipalTuringMulti.editar.isSelected()){
            framePrincipalTuringMulti.estadoSelecionado = this;
            checkPopup(evt);
        }
        
         if(framePrincipalTuringMulti.transicao.isSelected()){
            if(evt.getButton() == 1){
                framePrincipalTuringMulti.jPanel1.remove(framePrincipalTuringMulti.jPanel1.getComponentCount()-1);
                framePrincipalTuringMulti.jPanel1.repaint();
                
               
                Object[] retorno = transicaoTuring();                
                
                
                if( (int)retorno[0] == 0 ){                   
                        String transicao = "";
                        boolean transicaoExiste = false;
                        Transicao tExistente = null;

                        for ( Transicao t : estado.getTransicoes() ) {
                            if ( t.getEstadoDestino().equals(framePrincipalTuringMulti.estadoSelecionado.estado)  ) {
                                transicaoExiste = true;
                                tExistente = t;
                                break;
                            }
                        }

                        if(!transicaoExiste){
                            TreeSet<String> set = new TreeSet();
                            for(int i = 0; i < nFitas-1; i++){
                               
                               if( ((PainelFitas)retorno[1]).getFita().get(i).getLido().getText().equals(""))
                                   transicao += "□";
                               else 
                                   transicao += ((PainelFitas)retorno[1]).getFita().get(i).getLido().getText();
                               
                               transicao +=" ; ";
                               
                               if( ((PainelFitas)retorno[1]).getFita().get(i).getEscrito().getText().equals(""))
                                   transicao += "□";
                               else 
                                   transicao += ((PainelFitas)retorno[1]).getFita().get(i).getEscrito().getText();
                               
                               transicao +=" , ";
                               
                               transicao += String.valueOf(((PainelFitas)retorno[1]).getFita().get(i).getDirecao().getSelectedItem());
                               
                               transicao +=" | ";
                               
                               
                            }         
                            
                            if( ((PainelFitas)retorno[1]).getFita().get(nFitas-1).getLido().getText().equals(""))
                                transicao += "□";
                            else 
                                transicao += ((PainelFitas)retorno[1]).getFita().get(nFitas-1).getLido().getText();

                            transicao +=" ; ";

                            if( ((PainelFitas)retorno[1]).getFita().get(nFitas-1).getEscrito().getText().equals(""))
                                transicao += "□";
                            else 
                                transicao += ((PainelFitas)retorno[1]).getFita().get(nFitas-1).getEscrito().getText();

                            transicao +=" , ";

                            transicao += String.valueOf(((PainelFitas)retorno[1]).getFita().get(nFitas-1).getDirecao().getSelectedItem());
                               
                               
                               
                            set.add(transicao);
                            this.estado.getTransicoes().add(new Transicao(set,framePrincipalTuringMulti.estadoSelecionado.estado));
                                          
                        }
                        else {
                            for(int i = 0; i < nFitas-1; i++){
                               
                               if( ((PainelFitas)retorno[1]).getFita().get(i).getLido().getText().equals(""))
                                   transicao += "□";
                               else 
                                   transicao += ((PainelFitas)retorno[1]).getFita().get(i).getLido().getText();
                               
                               transicao +=" ; ";
                               
                               if( ((PainelFitas)retorno[1]).getFita().get(i).getEscrito().getText().equals(""))
                                   transicao += "□";
                               else 
                                   transicao += ((PainelFitas)retorno[1]).getFita().get(i).getEscrito().getText();
                               
                               transicao +=" , ";
                               
                               transicao += String.valueOf(((PainelFitas)retorno[1]).getFita().get(i).getDirecao().getSelectedItem());
                               
                               transicao +=" | ";
                               
                               
                            }         
                            
                            if( ((PainelFitas)retorno[1]).getFita().get(nFitas-1).getLido().getText().equals(""))
                                transicao += "□";
                            else 
                                transicao += ((PainelFitas)retorno[1]).getFita().get(nFitas-1).getLido().getText();

                            transicao +=" ; ";

                            if( ((PainelFitas)retorno[1]).getFita().get(nFitas-1).getEscrito().getText().equals(""))
                                transicao += "□";
                            else 
                                transicao += ((PainelFitas)retorno[1]).getFita().get(nFitas-1).getEscrito().getText();

                            transicao +=" , ";

                            transicao += String.valueOf(((PainelFitas)retorno[1]).getFita().get(nFitas-1).getDirecao().getSelectedItem());
                               
                            tExistente.getSimbolo().add(transicao);   
                            
                        }
                       framePrincipalTuringMulti.jPanel1.repaint();

                    
                
                }
            }
        }
    } 
    
    public void formMouseEntered(java.awt.event.MouseEvent evt) {
          if(framePrincipalTuringMulti.excluir.isSelected())
            framePrincipalTuringMulti.jPanel1.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon(getClass().getResource("/icones/x.png")).getImage(),new Point(10,10),"custom cursor"));      
           
          if(framePrincipalTuringMulti.transicao.isSelected())
             framePrincipalTuringMulti.estadoSelecionado = (DesenhoEstadoTuringMulti)evt.getComponent();    
           
    }
    
    private void checkPopup(MouseEvent evt) {
      if (evt.isPopupTrigger()) {
        framePrincipalTuringMulti.popup_menu.show(this, evt.getX(), evt.getY());
        
        if(estado.isFinal())
            framePrincipalTuringMulti.menu_final.setSelected(true);          
        else 
            framePrincipalTuringMulti.menu_final.setSelected(false); 
        
        if(estado.isInicial()) 
            framePrincipalTuringMulti.menu_inicial.setSelected(true);
        else 
           framePrincipalTuringMulti.menu_inicial.setSelected(false);
      }
      
      if(estado.getTransicoes().isEmpty())
          framePrincipalTuringMulti.menu_editar.setEnabled(false);
      else
          framePrincipalTuringMulti.menu_editar.setEnabled(true);
    }
     
    public Object[] transicaoTuring (){
        Object[] retorno = new Object[2]; 
        PainelFitas p = new PainelFitas(nFitas);       
      
        
        int i = JOptionPane.showConfirmDialog(framePrincipalTuringMulti,
        p,"Transição", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE,null);
        
        retorno[0] = i;
        retorno[1] = p;
        
        return retorno;
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
