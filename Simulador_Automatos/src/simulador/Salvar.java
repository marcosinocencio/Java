/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;

/**
 *
 * @author Vinicius
 */
public class Salvar implements Serializable{
     
     private ArrayList<Estado> estados;
     private ArrayList<Estado> tempEstados;
    
    private static final int STATEFINAL = 0;
    private static final int STATEINITIAL = 1;
    private static final int STATEINITIALFINAL = 2;
    private static final int STATENORMAL = 3;
     
     public Salvar(ArrayList<Estado> estados){
         this.estados = estados;                     
     }
     
    public Salvar(ArrayList<Estado> estados, String filePath){
         salvar(estados, filePath);   //         - gr <-> af 
    }
     
     private void salvar(ArrayList<Estado> estados, String filePath)
    {
        //System.out.println("");        
        
        int i = 0;    
        tempEstados = new ArrayList<>();
        for( Estado estado : estados )
        {           
            estado.setId(i++);            
            tempEstados.add(estado);
        }
   
        try
        {            
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            
            // structure - <structure>         
            Element structureElement = doc.createElement("structure");
            doc.appendChild(structureElement);
            
            // type - <type>fa</type>
            Element typeElement = doc.createElement("type");
            typeElement.appendChild(doc.createTextNode("fa"));
            structureElement.appendChild(typeElement);
            
            // automaton - <automaton>
            Element automatonElement = doc.createElement("automaton");
            structureElement.appendChild(automatonElement);            
                       
            for( Estado estado : estados )
            {
                String name = estado.getNome();
                String id = estado.getId();
                String x = String.valueOf(estado.getXCentral());
                String y = String.valueOf(estado.getYCentral());
                int type;
                
                if( estado.isFinal() && estado.isInicial() )
                {
                    type = STATEINITIALFINAL;
                }
                else if( estado.isInicial() )
                {
                    type = STATEINITIAL;
                }
                else if( estado.isFinal())
                {
                    type = STATEFINAL;
                }
                else
                {
                    type = STATENORMAL;
                }
                
                automatonElement.appendChild(createStateElement(doc, id, name, x, y, type));
            }
             
            for( Estado estado : estados )
            {
                String estadoId = estado.getId();
                
                for( Transicao transicao : estado.getTransicoes() )
                {
                    String transicaoDestinoId = getId(transicao.getEstadoDestino());
                    
                    for( String symbol : transicao.getSimbolo() )
                    {
//                        if( symbol == "λ" )
//                        {
//                            symbol = "";
//                        }
                        
                        automatonElement.appendChild(createTransitionElement(doc, estadoId, transicaoDestinoId, symbol));
                    }
                }
            }            
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();                
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath));

            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);

            transformer.transform(source, result);

            //System.out.println("File saved!");
        }
        catch(Exception e)
        {
             
        }
    }  
       
    private Element createStateElement(
            Document doc, 
            String id,
            String name,
            String x,
            String y,
            int stateType)
    {
        Element stateElement = doc.createElement("state");
        stateElement.setAttribute("id", id);
        stateElement.setAttribute("name", name);
        
        Element xElement = doc.createElement("x");
        xElement.appendChild(doc.createTextNode(x));
        
        Element yElement = doc.createElement("y");
        yElement.appendChild(doc.createTextNode(y));
                
        Element initialElement = doc.createElement("initial");
        Element finalElement = doc.createElement("final");
        
        switch( stateType )
        {
            case STATEINITIALFINAL:
                stateElement.appendChild(initialElement);
                stateElement.appendChild(finalElement);
                break;
            case STATEFINAL:                
                stateElement.appendChild(finalElement);
                break;
            case STATEINITIAL:                
                stateElement.appendChild(initialElement);
                break;
            case STATENORMAL:
                break;
        }
        
        stateElement.appendChild(xElement);
        stateElement.appendChild(yElement);
        
        return stateElement;
    }
    
    private Element createTransitionElement(
        Document doc,
            String from,
            String to,
            String symbol)
    {
        Element transitionElement = doc.createElement("transition");
        
        Element fromElement = doc.createElement("from");
        fromElement.appendChild(doc.createTextNode(from));
        transitionElement.appendChild(fromElement);
        
        Element toElement = doc.createElement("to");
        toElement.appendChild(doc.createTextNode(to));
        transitionElement.appendChild(toElement);
                
        Element symbolElement = doc.createElement("read");
        symbolElement.appendChild(doc.createTextNode(symbol));
        transitionElement.appendChild(symbolElement);
        
        return transitionElement;
    }
    
    private String getId(Estado currentEstado)
    {
        for( Estado estado : tempEstados)
        {
            if( currentEstado.getNome() == estado.getNome() )
            {
                return estado.getId();
            }
        }
        return "";
    }
    
     public ArrayList<Estado> getEstados() {
        return estados;
     }   
    
}
