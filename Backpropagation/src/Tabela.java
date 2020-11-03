/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
/**
 *
 * @author Vinicius
 */
public class Tabela extends AbstractTableModel{
    
    private int Neuronio   = 0;  //Coluna 1
    private int Peso   = 1;  //Coluna 2
    private int ValorPeso = 2;  //Coluna 3

    
    //Lista de nomes que serão exibidos
    private List<Peso> peso;

    
    public Tabela(){
        peso = new ArrayList();
    }
    
    
    public Tabela(List<Peso> lista){
        this();
        peso.addAll(lista);
    }

    public void selecionarLinha(int linha){
        peso.remove(linha);
    }

    public void inserir(Peso p){
        peso.add(p);       
        fireTableDataChanged();
    }
    
    public void excluir(int linha){
        //peso.remove(p);
        peso.remove(linha);      
        fireTableDataChanged();
    }
    
    
    //////////////////////////////////////
    ////Funções que Maniuplam a tabela////
    //////////////////////////////////////

    //Retorna o numero de linhas que a tabela tem
    @Override
    public int getRowCount(){
        //Cada produto na lista será uma linha
        return peso.size();
    }

    //Retorna o numero de colunas que a tabela tem
    @Override
    public int getColumnCount(){
        return 3;
    }  

    //retorna a classe que representa a coluna
    public Class getCloumnClass(int columnIndex){
        if(columnIndex == Neuronio){
            return String.class;
        }
        else if(columnIndex == Peso){
            return String.class;
        }
        else if(columnIndex == ValorPeso){
            return String.class;
        }
        return String.class;
        
    }
    
    //método utilizado pela JTable para escrever os valores nas células
    //Internamento a JTable passa em todas as celulas chamando este método para setar 
    //os valores;
    @Override
    public Object getValueAt(int rowIndex, int columnIndex){
        
        //Pega o procuto da linha
        Peso p = peso.get(rowIndex);
        
        //Verifica qual o valor deve ser retornado
        if(columnIndex == Neuronio){
            return p.getNeuronio();
        }
        else if(columnIndex == Peso){
            return p.getPeso();
        }
        else if(columnIndex == ValorPeso){
            return p.getValorPeso();
        }
        return String.class;
    }
    
    @Override
      public String getColumnName(int column){
        //qual o nome da coluna
        if(column == Neuronio){
            return "Neurônio";
        }
        else if(column == Peso){
            return "Peso";
        }
        else if(column == ValorPeso){
            return "Valor do Peso";
        }
        return "";
    }
    
    
    //Método que a JTabel chama quando uma célula é editada
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex){
       //Pega o produto da linha
        Peso p = peso.get(rowIndex);
        
        //Verifica qual o valor será alterado
        if(columnIndex == Neuronio){
            p.setNeuronio(aValue.toString());
        }
        else if(columnIndex == Peso){
            p.setPeso(aValue.toString());
        }
        else if(columnIndex == ValorPeso){
            p.setValorPeso(aValue.toString());
        }
        //avisa que os dados mudaram
        fireTableDataChanged();
    }
     @Override
     public boolean isCellEditable(int rowIndex, int columnIndex){
        //Somente a coluna dos pesos é editável
         switch (columnIndex) {
         case 0:
                return false;
         case 1:
                return false;
         case 2:
                return true;    
         default:
             return false;
         }
        
     }
}
