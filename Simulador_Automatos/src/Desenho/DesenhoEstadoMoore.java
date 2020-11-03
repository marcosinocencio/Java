/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Desenho;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import simulador.Estado;
import simulador.FramePrincipalMoore;
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
public class DesenhoEstadoMoore extends javax.swing.JPanel implements Serializable{
     /**
     * Creates new form Teste
     */     
    private Estado estado;
    private int flecha;  
    private boolean clicado;
    private transient FramePrincipalMoore framePrincipalMoore;
    private int x,y;  
    private Dimension area;  
    private Shape line;
    private DesenhoEstadoMoore aux;  
    private int j;
    
    public DesenhoEstadoMoore(Estado estado, FramePrincipalMoore framePrincipalMoore){        
        
        
        setSize(61, 61);
        
        area = new Dimension(0,0);
        this.framePrincipalMoore = framePrincipalMoore;
        this.setBackground(new Color(0,0,0,0));
        this.estado = estado;
        this.clicado = clicado; 
        
        JLabel saida = new JLabel(this.getEstado().getSaida());
        saida.setFont(new Font("Arial", Font.PLAIN, 15));
        saida.setOpaque(true);
        saida.setBackground(Color.yellow);
        saida.setBorder(BorderFactory.createLineBorder(Color.black));
        saida.setSize(saida.getPreferredSize());
        saida.setLocation(estado.getPosicaoSaida());
        framePrincipalMoore.jPanel1.add(saida);
        
        
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
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
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
            
                        
           
            g2d.drawString(estado.getNome(), 23 + flecha, 35);
         
    }
    
    public Estado getEstado() {
        return estado;
    }
    
    public void setEstado(Estado estado){
        this.estado = estado;
    }
    
    private void formMouseDragged(java.awt.event.MouseEvent evt) {                                  
         
        if(framePrincipalMoore.editar.isSelected() && SwingUtilities.isLeftMouseButton(evt) && getParent().getMousePosition()!=null){            
          
                int dx = getParent().getMousePosition().x - x;
                int dy = getParent().getMousePosition().y - y;            
                boolean changed = false; 


                  setLocation(x-30, y-30); 
                  
                  getParent().getComponent(j-1).setLocation(x+20, y-30);
                  getEstado().setPosicaoSaida(new Point(x+20, y-30));
                  if (getX() < 0 && getY() < 0){            
                   setLocation(0, 0); 
                  }
                  else if(getX() < 0)
                      setLocation(0, getY());
                  else if(getY() < 0)
                     setLocation(getX(), 0);
                  framePrincipalMoore.jPanel1.repaint();          
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
                    framePrincipalMoore.jPanel1.setPreferredSize(area);

                    //Let the scroll pane know to update itself
                    //and its scrollbars.
                    framePrincipalMoore.jPanel1.revalidate();
                }


              x += dx;
              y += dy;
          
        
       }
        
          if(framePrincipalMoore.transicao.isSelected()){
            if(SwingUtilities.isLeftMouseButton(evt)){
                Line2D shape =(Line2D)line;
                if(getParent().getMousePosition()!=null)
                    shape.setLine(shape.getP1(), getParent().getMousePosition());
                framePrincipalMoore.jPanel1.repaint();             
            } 
        }
    }                                 

    private void formMousePressed(java.awt.event.MouseEvent evt) {                                
           
        if(framePrincipalMoore.editar.isSelected() && evt.getButton() == 1){
            clicado = true;
            getParent().repaint();
            repaint(); 
            x = getParent().getMousePosition().x;
            y = getParent().getMousePosition().y;            
            
            for(int i = 0; i < getParent().getComponentCount(); i++ )                
                if( getParent().getComponent(i).equals(this)) {                 
                    j = i;
                    break;
                }
                    
        }
        if(framePrincipalMoore.editar.isSelected()){
            framePrincipalMoore.estadoSelecionado = this;
            checkPopup(evt);
        }
        
        if(framePrincipalMoore.transicao.isSelected()){
            if(evt.getButton() == 1){
                line = new Line2D.Double(getParent().getMousePosition(), getParent().getMousePosition());
                Linha a = new Linha(line,framePrincipalMoore.jPanel1.getSize());  
                framePrincipalMoore.jPanel1.add(a);
                framePrincipalMoore.jPanel1.repaint();
                aux = this;
            } 
        }
        
        if(framePrincipalMoore.excluir.isSelected()){
            if(evt.getButton() == 1){
                framePrincipalMoore.setEnabled(false);
                framePrincipalMoore.ExcluirTransicoes.setVisible(true);
                framePrincipalMoore.ExcluirTransicoes.setLocationRelativeTo(null);
                framePrincipalMoore.estadoSelecionadoLabel.setText(estado.getNome());
                DefaultComboBoxModel modelo = new DefaultComboBoxModel();
                framePrincipalMoore.estadoSelecionado = this;
                for ( Transicao e : estado.getTransicoes() )                     
                     modelo.addElement( e.getEstadoDestino().getNome() );
                
                framePrincipalMoore.estadoDestinoComboBox.setModel( modelo );
                framePrincipalMoore.estadoDestinoComboBox.setSelectedIndex(-1);
            }
        }
        
    }                                 

    private void formMouseReleased(java.awt.event.MouseEvent evt) {    
        String s;
        clicado = false;
        getParent().repaint();
        repaint();
        
        if (framePrincipalMoore.editar.isSelected()){
            framePrincipalMoore.estadoSelecionado = this;
            checkPopup(evt);
        }
        
         if(framePrincipalMoore.transicao.isSelected()){
            if(evt.getButton() == 1){
                framePrincipalMoore.jPanel1.remove(framePrincipalMoore.jPanel1.getComponentCount()-1);
                framePrincipalMoore.jPanel1.repaint();
                
               
                s = JOptionPane.showInputDialog(framePrincipalMoore,
                "Símbolo: ","Transição", JOptionPane.PLAIN_MESSAGE);
                
                if (s != null){
                       
                        boolean transicaoExiste = false;
                        Transicao tExistente = null;

                        for ( Transicao t : estado.getTransicoes() ) {
                            if ( t.getEstadoDestino().equals(framePrincipalMoore.estadoSelecionado.estado)  ) {
                                transicaoExiste = true;
                                tExistente = t;
                                break;
                            }
                        }

                        if(!transicaoExiste){
                            TreeSet<String> set = new TreeSet();
                            if (s.equals("") ){
                                set.add("λ");
                                this.estado.getTransicoes().add(new Transicao(set,framePrincipalMoore.estadoSelecionado.estado));
                            }
                            else {
                                set.add(s);
                                this.estado.getTransicoes().add(new Transicao(set,framePrincipalMoore.estadoSelecionado.estado));
                            }               
                        }
                        else {

                            if (s.equals("") )                        
                                tExistente.getSimbolo().add("λ");

                            else                         
                                tExistente.getSimbolo().add(s);     

                        }
                       framePrincipalMoore.jPanel1.repaint();

                }
            }
        }
    } 
    
    public void formMouseEntered(java.awt.event.MouseEvent evt) {
          if(framePrincipalMoore.excluir.isSelected())
            framePrincipalMoore.jPanel1.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon(getClass().getResource("/icones/x.png")).getImage(),new Point(10,10),"custom cursor"));      
           
          if(framePrincipalMoore.transicao.isSelected())
             framePrincipalMoore.estadoSelecionado = (DesenhoEstadoMoore)evt.getComponent();    
           
    }
    
    private void formMouseClicked(java.awt.event.MouseEvent evt) {
        if(framePrincipalMoore.editar.isSelected() && evt.getButton() == 1){
          String s = JOptionPane.showInputDialog(getParent(),
          "Insira a saída: ","Saída", JOptionPane.PLAIN_MESSAGE);
          JLabel a = (JLabel)getParent().getComponent(j-1);
          a.setFont(new Font("Arial", Font.PLAIN, 16));
          if(s == null)
            this.getEstado().setSaida(this.getEstado().getSaida());  
          else if(s.equals(""))            
            this.getEstado().setSaida("λ");
          else           
            this.getEstado().setSaida(s);          
          a.setText(this.getEstado().getSaida());
          a.setSize(a.getPreferredSize());
          
        }
    }
    
    private void checkPopup(MouseEvent evt) {
      if (evt.isPopupTrigger()) {
        framePrincipalMoore.popup_menu.show(this, evt.getX(), evt.getY());
                
        if(estado.isInicial()) 
            framePrincipalMoore.menu_inicial.setSelected(true);
        else 
           framePrincipalMoore.menu_inicial.setSelected(false);
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
