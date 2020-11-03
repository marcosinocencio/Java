/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Interface.TelaPrincipal.Registrador;

/**
 *
 * @author Vinicius
 */
public class Operacoes {
    
    TelaPrincipal tela;
    
    Operacoes(TelaPrincipal tela){
     this.tela = tela;    
    }
    
    public void imprime(){    
        tela.getjTextArea1().append(
                "A: ("+tela.A.sinal+", "+tela.A.magnitude+")    "
                + "B: ("+tela.B.sinal+", "+tela.B.magnitude+")    "
                + "C: ("+tela.C.sinal+", "+tela.C.magnitude+")    "
                + "D: ("+tela.D.sinal+", "+tela.D.magnitude+")\n");        
    }
    
    public void imprimeRegistradores(){    
        tela.getRegistradoresTexto().setText("A: ("+tela.A.sinal+", "+tela.A.magnitude+")    "
                + "B: ("+tela.B.sinal+", "+tela.B.magnitude+")    "
                + "C: ("+tela.C.sinal+", "+tela.C.magnitude+")    "
                + "D: ("+tela.D.sinal+", "+tela.D.magnitude+")\n");       
    }
    
    //Soma um ao registrador
    public void somaUm(Registrador r){
          if(r.sinal == 0)
              r.magnitude = r.magnitude + 1;
          else {
              r.magnitude = r.magnitude - 1;
          
          if(r.magnitude == 0)
             r.sinal = r.sinal - 1;
          }
    }  
    
    public void subtraiUm(Registrador r){
         
          if(r.magnitude == 0)
             r.sinal = r.sinal + 1;
        
          if(r.sinal == 0)
              r.magnitude = r.magnitude - 1;
          else 
              r.magnitude = r.magnitude + 1;
          
          
    }  
   
    //Atribui zero ao registrador
    public void atribuiZero(Registrador r){
         if(r.magnitude == 0)
            imprime();
         else {
            do{
                imprime();
                if(r.sinal == 0)
                   subtraiUm(r);
                else 
                   somaUm(r); 
                
                if(r.magnitude == 0){
                  imprime();
                  break;
                }
            }while(true);
         }        
    }   
    
    public void atribuiValor(Registrador r, int n, int sinal){
         atribuiZero(r);
         if(n == 0)
            System.out.print("");
         else {
            do{                
                if(sinal == 0)
                   somaUm(r); 
                else
                   subtraiUm(r);
                imprime();
                n = n - 1;
                if(n == 0)                  
                   break;
                
            }while(true);
         }  
    }  
    
    public void adicao(Registrador r, Registrador s){
           if(s.magnitude == 0)
               imprime();
           else{
                do{
                     
                     if(s.sinal == 0){
                         somaUm(r);
                         subtraiUm(s); 
                     }
                     else {
                         subtraiUm(r);
                         somaUm(s);
                     }
                     imprime();
                     if(s.magnitude == 0)
                        break;
                }while(true);
           }
    } 
    
    public void adicaoPres(Registrador r, Registrador s, Registrador t){
           if(s.magnitude == 0)
               imprime();
           else{
                atribuiZero(t);
                int temp = s.sinal;
                do{
                     somaUm(t);
                     if(s.sinal == 0){
                         somaUm(r);                         
                         subtraiUm(s);
                     }
                     else {
                         subtraiUm(r);                         
                         somaUm(s);
                     }
                     imprime();
                     if(s.magnitude == 0)
                        break;
                }while(true);
                
                do{
                     subtraiUm(t); 
                     if(temp == 0)
                         somaUm(s);                    
                     else 
                         subtraiUm(s);                        
                     
                     imprime();
                     if(t.magnitude == 0)
                        break;
                }while(true);
           }
    } 
    
    public void atribuiRegistrador(Registrador r, Registrador s, Registrador t){
               atribuiZero(r);
               adicaoPres(r,s,t);          
    }
    
    public void multiplica(Registrador r, Registrador s, Registrador t, Registrador u){
          
          if(r.magnitude == 0 || s.magnitude == 0){
              atribuiZero(r);
              if(r.sinal == 0)
                  System.out.print("");
              else
                 r.sinal = r.sinal - 1; 
          }
          else{  
          atribuiZero(t);          
                do{
                     somaUm(t);
                     if(r.sinal == 0)                        
                         subtraiUm(r);                    
                     else    
                         somaUm(r);
                     
                     imprime();
                     if(r.magnitude == 0)
                        break;
                }while(true);
                
                do{
                     adicaoPres(r,s,u);                                       
                     subtraiUm(t);                    
                     if(t.magnitude == 0)                      
                         break;                     
                }while(true);
         
        }
    }
    
    public boolean comparaMenor (Registrador a, Registrador b, Registrador c, Registrador d, Registrador e){
        
        atribuiRegistrador(c,a,e);
        atribuiRegistrador(d,b,e);
        boolean resultado = false;
        
        do{
          if(d.magnitude == 0)
              return false;
          else{              
              subtraiUm(c);
              subtraiUm(d);          
          }
         
          imprime();
           
          
          if(c.magnitude == 0)
              break;        
        }while(true);
        
         if(d.magnitude == 0)
              return false;
         else
              return true;  
        
    }
      
    public boolean modAB (Registrador a, Registrador b, Registrador c, Registrador d, Registrador e, Registrador x, Registrador y, Registrador z){
         atribuiRegistrador(c,a,e);
         atribuiRegistrador(d,b,e);
         
         if(b.magnitude == 0)
             return false;
         else{
             if (a.magnitude == 0)
                return true;
             else{
                 do{
                     adicaoPres(c,d,e);                     
                 }while(comparaMenor(c,b,x,y,z));
             }
             
             if(c.magnitude == 0)
                 return false;
             else
                 return true;
             
         }
          
    }
    
    public void fatorialCinco (Registrador a, Registrador b, Registrador c, Registrador d, Registrador e){
        
        do{
            if(e.magnitude == 0)
                break;
            b.magnitude = b.magnitude - 1;
            multiplica(a,b,c,d);
            imprime();           
            atribuiRegistrador(e,b,d);
            e.magnitude -= 1;
            
        }while(true);
    }
}
