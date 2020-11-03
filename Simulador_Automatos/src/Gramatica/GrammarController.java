/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gramatica;

import Gramatica.GrammarModel.Arrow;
import Gramatica.GrammarModel.Node;
import Gramatica.GrammarModel.Pair;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import simulador.Estado;
import simulador.FramePrincipal;

/**
 *
 * @author Teruya
 */
public class GrammarController 
{
    private GrammarView grammarView;
    private GrammarModel grammarModel;
    
    public GrammarController(GrammarView grammarView)
    {
        this.grammarView = grammarView;
        grammarView.getTestButton().addActionListener(testButtonListener);
        grammarView.getConvertButton().addActionListener(convertButtonListener);
    }
   
    public void testInput()
    {
        grammarView.getInputTextField().setBackground(Color.WHITE);
        if( grammarView.getInputTextField().getText().isEmpty() )
        {
            grammarView.getInputTextField().setBackground(Color.red);
            return;
        }
        
        grammarModel = new GrammarModel();
        ArrayList<GrammarView.GrammarLine> grammarLineList = grammarView.getGrammarLineList();
        for (GrammarView.GrammarLine grammarLine : grammarLineList) 
        {
            JTextField lhsTextField = grammarLine.getLhsTextField();
            JTextField rhsTextField = grammarLine.getRhsTextField();
            
            String lhsText = lhsTextField.getText();
            String rhsText = rhsTextField.getText();
            
            if( !lhsText.isEmpty() && rhsText.isEmpty() )
            {
                rhsTextField.setText("λ");
            }
            
            if( !lhsText.isEmpty() && !rhsText.isEmpty() )
            {
                grammarModel.add(lhsText,rhsText);
            }
        }
        
        grammarModel.print();
                
        checkInput();
    }
         
    public void checkInput()
    {       
        System.out.println("");
        List<Node> nodeList = grammarModel.getNodeList();
        for (Node node : nodeList) 
        {
            node.color = Color.WHITE;
        }
        
        Node currentNode = grammarModel.getNodeList().get(0);      
        for( int i = 0; i<grammarView.getInputTextField().getText().length(); i++ )
        {
            String currentLetter = Character.toString(grammarView.getInputTextField().getText().charAt(i));
            currentNode = print(currentNode, currentLetter);
//            if( i == grammarView.getInputTextField().getText().length() )
//            {
//                System.out.println(currentLetter);
//                grammarView.getInputTextField().setBackground(Color.GREEN);
//                return;
//            }

            System.out.println("found then..." + String.valueOf(i) + " " + currentLetter);
            if( currentNode == null )
            {                
                grammarView.getInputTextField().setBackground(Color.red);
                return;
            }
            else if( i == grammarView.getInputTextField().getText().length() - 1 )
            {                                
                grammarView.getInputTextField().setBackground(Color.GREEN);
                return;                                
            }
            for (Node node : nodeList) 
            {
                node.color = Color.WHITE;
            }            
        }
        
//        Node currentNode = grammarModel.currentNode();
    }
    
    public Node print(Node node, String letter)
    {
        node.color = Color.GRAY;
        System.out.println("Node " + node.name);
        Node returnNode = null;
        for (Arrow arrow : node.arrowList) 
        {
            System.out.println(arrow.string());
            if( letter.equals(arrow.name) )
            {
                System.out.println(" - - found " + letter + " = " + arrow.name);
                return arrow.node;
            }
            else if( arrow.node != null && arrow.node.color == Color.WHITE )
            {                                
                returnNode = print(arrow.node,letter);
                if( returnNode != null )
                {
                    return returnNode;
                }
                else return null;
            }                        
        }
        node.color = Color.BLACK;   
        return returnNode;
    }
    
//    public void print(Node node, int index)
//    {
//        if( node.color == Color.BLACK )
//        {
//            return;
//        }
//        
//        System.out.println("Node " + node.name);
//        node.color = Color.GRAY;        
//        for (Arrow arrow : node.arrowList) 
//        {
//            if( arrow.node != null )
//            {
//                System.out.println(" - Arrow " + arrow.name + " " + String.valueOf(index) + " to " + arrow.node.name);
//            }
//            else
//            {
//                System.out.println(" - Arrow " + arrow.name + " " + String.valueOf(index) + " to null");
//            }
//            
////            System.out.println(String.valueOf(index) + " " + String.valueOf(grammarView.getInputTextField().getText().length()));
//            if( index == grammarView.getInputTextField().getText().length() )
//            {
//                for (Node aux : grammarModel.getNodeList()) 
//                {
//                    node.color = Color.BLACK;                    
//                }
//                grammarView.getInputTextField().setBackground(Color.GREEN);
//                return;
//            }
//            
//            String currentLetter = Character.toString(grammarView.getInputTextField().getText().charAt(index));   
//            if( arrow.node != null && currentLetter.equals(arrow.name) )
//            {
//                for (Node aux : grammarModel.getNodeList()) 
//                {
//                    node.color = Color.WHITE;
//                }
//                index++;
//            }
//            if( arrow.node != null && arrow.node.color == Color.WHITE )
//            {         
//                System.out.println(currentLetter + " -> " + arrow.name);
//                print(arrow.node, index);
//            }                        
//        }
//        node.color = Color.BLACK;        
//    }
//    
    private ActionListener testButtonListener = (ActionEvent e) -> {
        testInput();
    };
    
    private ActionListener convertButtonListener = (ActionEvent e) -> {
        convertGrToFa();
    };
    
    private void convertGrToFa(){
        ArrayList<GrammarView.GrammarLine> grammarLineList = grammarView.getGrammarLineList();
        int i = 0;
        for (GrammarView.GrammarLine grammarLine : grammarLineList){
            JTextField lhsTextField = grammarLine.getLhsTextField();
            JTextField rhsTextField = grammarLine.getRhsTextField();
            
            String lhsText = lhsTextField.getText();
            String rhsText = rhsTextField.getText();
            
            if( !lhsText.isEmpty() && !rhsText.isEmpty() )
            {
               i++;
               break; 
            }
        }
        
        if(i == 0)
                JOptionPane.showMessageDialog(null, "Crie uma grámatica para converter para autômato finito", "Advertência",  
                JOptionPane.WARNING_MESSAGE);
        else{
            GramaticaConversor gramaticaConversor = new GramaticaConversor(createGrammarModel().getNodeList());
            gramaticaConversor = new GramaticaConversor(createGrammarModel().getNodeList());
            ArrayList<Estado> estados = gramaticaConversor.converterGramaticaParaAF();
            FramePrincipal f = new FramePrincipal();
            f.setVisible(true);            
            f.gramaticaParaAutomatoFinito(estados);
        }
    }
    
    private GrammarModel createGrammarModel(){
        grammarModel = new GrammarModel();
        ArrayList<GrammarView.GrammarLine> grammarLineList = grammarView.getGrammarLineList();
        for (GrammarView.GrammarLine grammarLine : grammarLineList) 
        {
            JTextField lhsTextField = grammarLine.getLhsTextField();
            JTextField rhsTextField = grammarLine.getRhsTextField();
            
            String lhsText = lhsTextField.getText();
            String rhsText = rhsTextField.getText();
            
            if( !lhsText.isEmpty() && rhsText.isEmpty() )
            {
                rhsTextField.setText("λ");
            }
            
            if( !lhsText.isEmpty() && !rhsText.isEmpty() )
            {
                grammarModel.add(lhsText,rhsText);
            }
        }
        return grammarModel;
    }
    
    public void convertAftoGr(ArrayList<Pair> array){
        int n = array.size()-1;
        
        for(int i = 0; i < n; i++)
            grammarView.addGrammarPanel();
        
        ArrayList<GrammarView.GrammarLine> grammarLineList = grammarView.getGrammarLineList();
        
        for(int i = 0; i < array.size(); i++){
            grammarLineList.get(i).getLhsTextField().setText(array.get(i).getLhsEstado().getNome());
            grammarLineList.get(i).getRhsTextField().setText(array.get(i).getSimbolo()+array.get(i).getRhsEstado().getNome());
        }
    }
}
