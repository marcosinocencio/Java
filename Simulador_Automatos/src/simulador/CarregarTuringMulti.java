/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador;

import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.TreeSet;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

/**
 *
 * @author Teruya
 */
public class CarregarTuringMulti
{
    private ArrayList<Estado> stateList;
    private boolean isVersion7 = false;
    private boolean hasState = false;
    private boolean hasTransition = false;
    private int nFitas;
    
    public CarregarTuringMulti(String filePath)
    {
        stateList = new ArrayList<>();
        
        //System.out.println("");
        try
        {
            File fXmlFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            
            NodeList stateNodeList = doc.getElementsByTagName("block");
            if( stateNodeList.getLength() > 0 )
            {
                hasState = true;
                for( int i = 0; stateNodeList.getLength()>i; i++ )
                {
                    
                   
                        Element stateElement = (Element) stateNodeList.item(i);
                        
                        Node posicao = stateElement.getElementsByTagName("x").item(0).getFirstChild();
                        posicao.setNodeValue(Integer.toString(new Double(posicao.getNodeValue()).intValue()));
                       
                        posicao = stateElement.getElementsByTagName("y").item(0).getFirstChild();
                        posicao.setNodeValue(Integer.toString(new Double(posicao.getNodeValue()).intValue()));
                        
                        
                        Estado estado = createEstado(stateElement);
                        if( estado != null )
                        {
                            stateList.add(estado);
                        }
                    
                }
            }
            
            NodeList transitionNodeList = doc.getElementsByTagName("transition");
            if( transitionNodeList.getLength() > 0 )
            {
                hasTransition = true;
                nFitas = Integer.parseInt(doc.getElementsByTagName("tapes").item(0).getTextContent());
                
                for( int i = 0; transitionNodeList.getLength()>i; i++ )
                {
                    Node node = transitionNodeList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE)
                    {
                        Element transitionElement = (Element) node;                      
                        String fromId;
                        String toId;
                        String read;
                        String write;
                        String move;
                        String tran="";               
                        
                       
                        for(int j = 0; j < nFitas - 1 ; j++){                            
                            read = transitionElement.getElementsByTagName("read").item(j).getTextContent();
                            write = transitionElement.getElementsByTagName("write").item(j).getTextContent();
                            move = transitionElement.getElementsByTagName("move").item(j).getTextContent();
                            
                            if(read.equals(""))
                                 read = "□";
                            if(write.equals(""))
                                 write = "□";
                            tran += read+" ; "+write+" , "+move+" | ";
                        }
                        
                            fromId = getValue(transitionElement, "from");
                            toId = getValue(transitionElement, "to");
                            read = transitionElement.getElementsByTagName("read").item(nFitas - 1).getTextContent();
                            write = transitionElement.getElementsByTagName("write").item(nFitas - 1).getTextContent();
                            move = transitionElement.getElementsByTagName("move").item(nFitas - 1).getTextContent();
                            if(read.equals(""))
                                 read = "□";
                            if(write.equals(""))
                                 write = "□";                           
                            tran += read+" ; "+write+" , "+move;
                            
                            int fromIndex = getEstadoIndexById(fromId);
                            int toIndex = getEstadoIndexById(toId);
                        
                        
                        
                        if( stateList.get(fromIndex) != null && stateList.get(toIndex) != null )
                        {
                            boolean existTransicaoFromTo = true;
                            for( Transicao transicao : stateList.get(fromIndex).getTransicoes() )
                            {
                                if( transicao.getEstadoDestino() == stateList.get(toIndex) )
                                {                                      
                                    transicao.getSimbolo().add(tran);
                                    existTransicaoFromTo = false;
                                }
                            }
                            if( existTransicaoFromTo )
                            {
                                TreeSet<String> treeSet = new TreeSet<>();                           
                                treeSet.add(tran);
                                stateList.get(fromIndex).getTransicoes().add(new Transicao(treeSet, stateList.get(toIndex)));
                            }
                            
                        }
                    }
                }
            }
            
            // print for just test
            //            for( Estado estado : stateList )
            //            {
            //                System.out.println(estado.getNome() + ", init: " + estado.isInicial() + ", end: " + estado.isFinal());
            //                for( Transicao transicao : estado.getTransicoes() )
            //                {
            //                    System.out.println(" -> " + transicao.getSimbolo() +
            //                                       "  -> " + transicao.getEstadoDestino().getNome() +
            //                                       ", init: " + transicao.getEstadoDestino().isInicial() + ", end: " + transicao.getEstadoDestino().isFinal());
            //                }
            //            }
        }
        catch(Exception e)
        {
            
        }
    }

    public int getnFitas() {
        return nFitas;
    }   
    
    private int getEstadoIndexById(String id)
    {
        int i = 0;
        for( Estado estado : stateList )
        {
            if( estado.getId().equals(id) )
            {
                //System.out.println("return " + i);
                return i;
            }
            i++;
        }
        return 0;
    }
    
    private String getValue(Element element, String name)
    {
        NodeList nodeList = element.getElementsByTagName(name);
        for( int i = 0; nodeList.getLength() > i; i++ )
        {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element currentElement = (Element) node;
                return currentElement.getTextContent();
            }
        }
        return "λ";
    }
    
    private Estado createEstado(Element element)
    {
        String name = element.getAttribute("name");
        String id = element.getAttribute("id");
        int x = 0;
        int y = 0;
        
        boolean isInitial = false;
        boolean isFinal = false;
        Point p = new Point(0,0);
        
        NodeList nodeList = element.getChildNodes();
        for( int i = 0; nodeList.getLength() > i; i++ )
        {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element currentElement = (Element) node;
                if( currentElement.getNodeName() == "x" )
                {
                    x = Integer.valueOf(currentElement.getTextContent());
                }
                else if( currentElement.getNodeName() == "y" )
                {
                    y = Integer.valueOf(currentElement.getTextContent());
                }
                else if( currentElement.getNodeName() == "initial" )
                {
                    isInitial = true;
                }
                else if( currentElement.getNodeName() == "final" )
                {
                    isFinal = true;
                }
            }
        }
        
        Estado estado = new Estado(name, isInitial, isFinal, p);
        estado.setId(Integer.valueOf(id));
        estado.setXCentral(x);
        estado.setYCentral(y);
        
        return estado;
    }
    
    private Element getElementByName(Document doc, String name)
    {
        NodeList nodeList = doc.getElementsByTagName(name);
        for( int i = 0; nodeList.getLength()>i; i++ )
        {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                return (Element) node;
            }
        }
        return null;
    }
    
    public boolean isVersion7()
    {
        if( hasState && hasTransition )
            return isVersion7;
        else return false;
    }
    
    public ArrayList<Estado> getEstados()
    {
        return stateList;
    }
}
