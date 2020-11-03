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
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import simulador.Estado;
import simulador.Transicao;

/**
 *
 * @author Teruya
 */
public class GramaticaConversor 
{
    private List<Node> nodeList;
    private Point p = new Point(0,0);
    
    public GramaticaConversor()
    {
              
    }
    
    
    public GramaticaConversor(List<Node> nodeList)
    {
        this.nodeList = nodeList;        
    }

    public ArrayList<Estado> converterGramaticaParaAF()
    {
        if( nodeList == null )
        {
            return null;
        }
        
        System.out.println("");
        System.out.println("START GRAMMAR TO AUTOMATON");
        
        ArrayList<Estado> estados = new ArrayList<>();
              
        Node startNode = getStart();
        Node finalNode = getFinal();
        if( startNode == null && finalNode == null )
        {            
            return null;
        }
        System.out.println("start: " + startNode.name + ",end: " + finalNode.name);
        
        for( Node node : nodeList )
        {
            String nome  = node.name;
            
            boolean isStart = false;
            if( startNode == node )
            {
                isStart = true;
            }                      
            
            boolean isFinal = false;
            if( finalNode == node )
            {
                isFinal = true;
            }                      
            
            Estado estado = new Estado(nome, isStart, isFinal, p);
            estados.add(estado);
            
            for( Arrow arrow : node.arrowList )
            {
                boolean isDestinyStart = false;
                if( startNode == arrow.node )
                {
                    isDestinyStart = true;
                }                      

                boolean isDestinyFinal = false;
                if( finalNode == arrow.node )
                {
                    isDestinyFinal = true;
                }
                
             
                boolean hasEstadoDestino = false;
                for( Transicao transicao : estado.getTransicoes() )
                {
                    if( transicao.getEstadoDestino() != null && transicao.getEstadoDestino().getNome() == arrow.node.name )
                    {
                        transicao.getSimbolo().add(arrow.name);
                        hasEstadoDestino = true;
                    }
                }
                if( !hasEstadoDestino )
                {
                    TreeSet<String> treeSetName = new TreeSet<>();
                    treeSetName.add(arrow.name);
                    estado.getTransicoes().add(new Transicao(treeSetName, new Estado(arrow.node.name, isDestinyStart, isDestinyFinal, p)));
                }                
            }
        }      
        estados.add(new Estado(finalNode.name, false, true, p));
        
        // print for just test       
//        for( Estado estado : estados )
//        {
//            System.out.println(estado.getNome() + ", init: " + estado.isInicial() + ", end: " + estado.isFinal());
//            for( Transicao transicao : estado.getTransicoes() )
//            {
//                System.out.println(" -> " + transicao.getSimbolo() + 
//                                   "  -> " + transicao.getEstadoDestino().getNome() + 
//                                   ", init: " + transicao.getEstadoDestino().isInicial() + ", end: " + transicao.getEstadoDestino().isFinal());
//            }
//        }
        
        return estados;
    }
    
    private Node getStart()
    {
        for( Node node : nodeList )
        {
            node.color = Color.WHITE;
        }
        
        for( Node node : nodeList )
        {
            for( Arrow arrow : node.arrowList )
            {
                if( arrow.node!=null && arrow.node != node )
                {
                    arrow.node.color = Color.GRAY;
                }                
            }
        }
        
        for( Node node : nodeList )
        {
            System.out.println(node.color);
            if( node.color == Color.WHITE )
            {                
                return node;
            }
        }
        
        return null;
    }
    
    private Node getFinal()
    {       
        Node finalNode = new Node();
        finalNode.name = "Z";
        
        for( Node node : nodeList )
        {
            for( Arrow arrow : node.arrowList )
            {
                if( arrow.node == null )
                {                
                    System.out.println("per final: " + node.name);
                    arrow.node = finalNode;                    
                }
            }
        }
        
        return finalNode;
    }

    public ArrayList<Pair> converterAFparaGramatica(ArrayList<Estado> estados) 
    {
        ArrayList<Pair> pairList = new ArrayList<>();
                               
        for( Estado estado : estados )
        {                       
            for( Transicao transicao : estado.getTransicoes() )
            {               
                for( String symbol : transicao.getSimbolo() )
                {
                    if( symbol.equals("λ") )
                    {
                        symbol = "";
                    }
                    Estado originEstado = new Estado(estado.getNome(), estado.isInicial(), estado.isFinal(), p);                                        
                    Estado destinyEstado = new Estado(transicao.getEstadoDestino().getNome(), transicao.getEstadoDestino().isInicial(), transicao.getEstadoDestino().isFinal(), p);
                    
                    Pair pair = new Pair(originEstado, destinyEstado, symbol);                    
                    pairList.add(pair);                         
                }
            }
            
            if( estado.isFinal() )
            {                               
                Estado originEstado = new Estado(estado.getNome(), estado.isInicial(), estado.isFinal(), p);                                        
                Estado destinyEstado = new Estado("λ", false, false, p);

                Pair pair = new Pair(originEstado, destinyEstado, "");                    
                pairList.add(pair); 
            }
        }
        
        // for test        
        for( Pair pair : pairList )
        {                     
            String gramatica = pair.getGramatica();
            System.out.println(gramatica);            
        }
        
        return pairList;
    }
    
    private boolean hasSymbol(ArrayList<String> symbolList, String symbol)
    {
        for( String currentSymbol : symbolList )
        {
            if( currentSymbol.equals(symbol) )
            {
                return true;
            }
        }
        return false;
    }
    
}
