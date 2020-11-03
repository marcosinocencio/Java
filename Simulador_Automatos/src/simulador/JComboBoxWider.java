/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador;

import javax.swing.JComboBox;
import java.awt.Dimension;
import javax.swing.ComboBoxModel;

public class JComboBoxWider extends JComboBox {
    private int listWidth=0;
    
    public JComboBoxWider() {
        
    } 
    
    public JComboBoxWider(Object[] objs) {
        super(objs);
    } 
    
    public JComboBoxWider(ComboBoxModel aModel) { 
        super(aModel); 
    } 

    @Override
    public Dimension getSize() {
        Dimension dim = super.getSize();
        if (listWidth>0)
            dim.width = listWidth;
        return dim;
    }

    public void setListWidth(int listWidth) {
        this.listWidth = listWidth;
    }
}
