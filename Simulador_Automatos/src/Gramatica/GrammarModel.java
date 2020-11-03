/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gramatica;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import simulador.Estado;

/**
 *
 * @author Teruya
 */
public class GrammarModel 
{
    public List<Node> nodeList;
    
    public GrammarModel()
    {
        nodeList = new ArrayList<Node>();
    }
    
    public static class Node
    {
        public String name;
        public ArrayList<Arrow> arrowList = new ArrayList<>();
        public Color color;
        
        public Node()
        {
            System.out.println("=========== node created");
        }
    }
    
    public class Arrow
    {
        public String name = "";
        public Node node = null;
        
        public Arrow(String name, Node node)
        {
            this.name = name;
            this.node = node;
            
            print();
        }

        public void print()
        {
            if(  node == null )
            {
                System.out.println(" - - - arrow created -> [" + name + "] -> to null");
            }
            else
            {
                System.out.println(" - - - arrow created -> [" + name + "] -> to " + node.name);
            }
        }
        
        public String string()
        {
            if(  node == null )
            {
                return " - arrow -> [" + name + "] -> to null";                
            }
            else
            {
                return " - arrow -> [" + name + "] -> to " + node.name;                
            }
        }
    }
    
    public static class Pair
    {
        private Estado lhsEstado;
        private Estado rhsEstado;
        private String symbol;
        private String gramatica = "";
        
        public Pair(Estado lhsEstado, Estado rhsEstado, String simbolo)
        {
            this.lhsEstado = lhsEstado;
            this.rhsEstado = rhsEstado;
            this.symbol = simbolo;
        }      

        public Estado getLhsEstado() {
            return lhsEstado;
        }

        public Estado getRhsEstado() {
            return rhsEstado;
        }

        public void setLhsEstado(Estado lhsEstado) {
            this.lhsEstado = lhsEstado;
        }

        public void setRhsEstado(Estado rhsEstado) {
            this.rhsEstado = rhsEstado;
        }       

        public String getSimbolo() {
            return symbol;
        }

        public void setSimbolo(String symbol) {
            this.symbol = symbol;
        }     

        public String getGramatica() {
            return getLhsEstado().getNome() + " -> " + getSimbolo() + getRhsEstado().getNome();
        }       
    }
    
    public void add(String terminal, String symbol)
    {
        Node currentNode = currentNode(terminal);
        
        // if current terminal not exist
        if( currentNode == null )
        {
            System.out.println("current node null " + terminal);
            currentNode = new Node();
            currentNode.name = terminal;
            nodeList.add(currentNode);
        }
        // if current terminal already exists
        else
        {
            System.out.println("current node " + terminal);
        }
        
        int lenght = symbol.length();
        if( lenght == 1 )
        {
            char current = symbol.charAt(0);
            currentTransiction(currentNode, current);
        }
        else if( lenght == 2 )
        {
            char left = symbol.charAt(0);
            char right = symbol.charAt(1);
            currentTransiction(currentNode, left, right);
        }
    }
    
    public Node currentNode(String terminal)
    {
        for (Node node : nodeList) 
        {            
            if( node.name.equals(terminal) )
            {
                return node;
            }
        }
        return null;           
    }

    public void currentTransiction(Node node, char name)
    {
        // go to [ node ] -> "" -> [ null ]
        if( Character.toString(name).equals("λ") )
        {
            System.out.println(" - finish " + name);
            node.arrowList.add(new Arrow("λ", null));
        }
        // go to [ node ] -> "name" -> [ null ]
        else if( Character.isLowerCase(name) )
        {
            System.out.println(" - transiction " + name);   
            node.arrowList.add(new Arrow(Character.toString(name), null));
        }
        // go to [ node ] -> "" -> [ name ]
        else
        {
            System.out.println(" - go to " + name);
            Node currentNode = null;
            for( Arrow arrow : node.arrowList )
            {
                if( arrow.name.equals(Character.toString(name)) )
                {
                    System.out.println(" - - exist arrow go to " + currentNode(Character.toString(name)).name);
                    currentNode = arrow.node;
                }
            }
            if( currentNode == null )
            {
                System.out.println(" - - not exist arrow go to " + name);
                currentNode = currentNode(Character.toString(name));
                if( currentNode == null )
                {
                    System.out.println(" - - not exist node -> create " + name);                    
                    currentNode = new Node();
                    currentNode.name = Character.toString(name);
                    nodeList.add(currentNode);
                }
            }
            node.arrowList.add(new Arrow("", currentNode));
        }
    }
    
    public void currentTransiction(Node currentNode, char name, char to)
    {
        String arrowName = Character.toString(name);
        String arrowNodeName = Character.toString(to);
                
        Node arrowNode = currentNode(arrowNodeName);
        if( arrowNode == null )
        {
            System.out.println(" - - not exist node -> create " + arrowNodeName);
            arrowNode = new Node();
            arrowNode.name = arrowNodeName;
            nodeList.add(arrowNode);
        }
        currentNode.arrowList.add(new Arrow(arrowName, arrowNode));
    }

    public List<Node> getNodeList()
    {
        return nodeList;
    }

    public void print()
    {        
        System.out.println("");
        for (Node node : nodeList) 
        {
            node.color = Color.WHITE;
        }
        
        for (Node node : nodeList) 
        {
            if( node.color == Color.WHITE )
            {
                print(node);
            }
        }
    }
        
    public void print(Node node)
    {
        node.color = Color.GRAY;
        System.out.println("Node " + node.name);
        for (Arrow arrow : node.arrowList) 
        {
            System.out.println(" - Arrow " + arrow.name);
            if( arrow.node != null && arrow.node.color == Color.WHITE )
            {                                
                print(arrow.node);
            }                        
        }
        node.color = Color.BLACK;        
    }
}
