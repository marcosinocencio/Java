/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gramatica;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 *
 * @author Teruya
 */
public class GrammarView 
{
    private JPanel mainPanel;
    private int i = 0;
    private JPanel gridbagPanel;
    
    private ArrayList<GrammarLine> grammarLineList;
    private JButton testButton;
    private JButton convertButton;
    private JTextField inputTextField;
    
    public GrammarView() 
    {
        
              
        grammarLineList = new ArrayList<GrammarLine>();
        
        JFrame frame = new JFrame("Gramática");
        
        frame.setMinimumSize(new Dimension(100, 100));
        frame.setSize(400, 600);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        
        gridbagPanel = new JPanel();
        gridbagPanel.setLayout(new GridBagLayout());  

        gridbagPanel.add(buttonPanel(), gridBagConstraints());
        gridbagPanel.add(inputPanel(), gridBagConstraints());
        
        mainPanel = new JPanel(new BorderLayout());    
        mainPanel.add(gridbagPanel, BorderLayout.NORTH);
                
        JScrollPane jScrollPanel = new JScrollPane(mainPanel);
        frame.add(jScrollPanel);        
        
          addGrammarPanel();
//          addGrammarPanel("S","a");
//          addGrammarPanel("S","aK");
//          addGrammarPanel("K","bK");
//          addGrammarPanel("K","L");
//          addGrammarPanel("L","cL");
//          addGrammarPanel("L","");
        frame.setVisible(true);
    }
    
    public JButton getTestButton() 
    {
        return testButton;
    }
    
    public JButton getConvertButton() 
    {
        return convertButton;
    }

    public JTextField getInputTextField() 
    {
        return inputTextField;
    }

    public ArrayList<GrammarLine> getGrammarLineList() 
    {
        return grammarLineList;
    }    

    public class GrammarLine
    {
        private JTextField lhsTextField;
        private JTextField rhsTextField;
        
        GrammarLine(JTextField lhsTextField, JTextField rhsTextField)
        {
            this.lhsTextField = lhsTextField;
            this.rhsTextField = rhsTextField;
        }

        public JTextField getLhsTextField() 
        {
            return lhsTextField;
        }

        public void setLhsTextField(JTextField lhsTextField) 
        {
            this.lhsTextField = lhsTextField;
        }

        public JTextField getRhsTextField() 
        {
            return rhsTextField;
        }

        public void setRhsTextField(JTextField rhsTextField) 
        {
            this.rhsTextField = rhsTextField;
        }
    }
    
    public GridBagConstraints gridBagConstraints()
    {
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = i++;
        gc.anchor = GridBagConstraints.NORTHWEST;
        gc.insets = new Insets(2, 0, 0, 2);
        return gc;
    }
    
    public JPanel buttonPanel()
    {
        JPanel buttonPanel = new JPanel();
        JButton button = new JButton("Adicionar campo");
        buttonPanel.add(button);
        button.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {                                
                addGrammarPanel();
            }
        });
        return buttonPanel;
    }
    
    public JPanel inputPanel()
    {
        JPanel inputPanel = new JPanel();
         
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints(); 
        
        inputTextField = new JTextField(15);
        c.gridx = 0;
        c.gridy = 0;       
        inputPanel.add(inputTextField, c);
        
        testButton = new JButton("Simular");              
        c.gridx = 1;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;
        c.insets = c.insets = new Insets(0,5,0,0); 
        inputPanel.add(testButton, c);
        
        convertButton = new JButton("Converter para autômato finito");
        
        
        c = new GridBagConstraints();        
        c.gridx = 1;
        c.gridy = 1;
        c.insets = c.insets = new Insets(5,5,0,0); 
               
        inputPanel.add(convertButton, c);

        return inputPanel;
    }
    
    public void addGrammarPanel()
    {
        gridbagPanel.add(grammarPanel(), gridBagConstraints());

        mainPanel.add(gridbagPanel, BorderLayout.NORTH);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    
    public void addGrammarPanel(String terminal, String symbol)
    {
        gridbagPanel.add(grammarPanel(terminal,symbol), gridBagConstraints());

        mainPanel.add(gridbagPanel, BorderLayout.NORTH);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    
    public JPanel grammarPanel()
    {
        JPanel grammarPanel = new JPanel();
               
        JTextField lhsTextField = new JTextField(5);
        grammarPanel.add(lhsTextField);
        
        JLabel label = new JLabel();
        label.setText("->");
        grammarPanel.add(label);
        
        JTextField rhsTextField = new JTextField(5);
        grammarPanel.add(rhsTextField);
        
        grammarLineList.add(new GrammarLine(lhsTextField, rhsTextField));
        
        return grammarPanel;
    }
    
    public JPanel grammarPanel(String terminal, String symbol)
    {
        JPanel grammarPanel = new JPanel();
               
        JTextField lhsTextField = new JTextField(5);
        lhsTextField.setText(terminal);
        grammarPanel.add(lhsTextField);
        
        JLabel label = new JLabel();
        label.setText("->");
        grammarPanel.add(label);
        
        JTextField rhsTextField = new JTextField(5);
        rhsTextField.setText(symbol);
        grammarPanel.add(rhsTextField);
        
        grammarLineList.add(new GrammarLine(lhsTextField, rhsTextField));
        
        return grammarPanel;
    }
}
