/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pdi;
import java.io.*;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


/**
 *
 * @author Vinicius
 */
public class Funcoes {
    
    private int[][] matriz = new int[1200][1200]; //lê imagem e passa para essa matriz
    private int[][] RED = new int[1200][1200];  //usa essa matriz quando manipula imagens coloridas que cada pixel tem três valores 
    private int[][] GREEN = new int[1200][1200]; //usa essa matriz quando manipula imagens coloridas que cada pixel tem três valores 
    private int[][] BLUE = new int[1200][1200]; //usa essa matriz quando manipula imagens coloridas que cada pixel tem três valores 
    private short x,y; //guarda valores do tamanho da imagem 
    private String formato; // guarda formato da imagem se é P3(imagem colorida) ou P2(imagem em tom de cinza) 
    
      
    public void le(String nomeimagem) throws FileNotFoundException, IOException{
        //essa é a função que lê linha por linha da imagem e passa para a matriz
        File im = new File(nomeimagem); 
        Scanner s = new Scanner(im);
        formato = s.nextLine();//P2 ou P3
        
        s.nextLine();//Comentario
        
        x = Short.parseShort(s.next());        
        y = Short.parseShort(s.next());
               
        s.next();//tons de cinza 
        if (formato.equals("P2")){
            
        for(int i=0;i<x;i++){
            for(int j=0;j<y;j++)
               matriz[i][j] = Integer.parseInt(s.next());  //faz cast para inteiro porque o Scanner lê valor por valor em formato de String
          }  
        }
        
        else{
           for(int i=0;i<x;i++){
               for(int j=0;j<y;j++){
                    RED[i][j] = Integer.parseInt(s.next());  // RED GREEN BLUE são matriz usadas para armanezar o valor de cada 
                    GREEN[i][j] = Integer.parseInt(s.next()); //pixel que é formado por 3 valores quando a imagem a imagem é colorida
                    BLUE[i][j] = Integer.parseInt(s.next()); 
                }
            } 
        }
        s.close();
      }
    
    public void escreve(String nomeimagem) throws IOException{
      //Função que salva a matriz ou as matrizes, no caso da imagem colorida, para arquivo   
            
      File nim = new File(nomeimagem); 
      FileWriter w = new FileWriter(nim);
      
      if (formato.equals("P2")){ //verifica o formato para salvar no arquivo, se for P2 usa a matriz
      
      w.write(formato+"\n");
      w.write("# Created by Marcos\n");
      w.write(x+" "+ y + "\n");
      w.write("255"+"\n");
        for(int i=0;i<x;i++){
            for(int j=0;j<y;j++){
               w.write(matriz[i][j]+" ");
           }
           w.write("\n"); 
        }
        
        w.close();
      }
      
      else{ 
       
        w.write(formato+"\n"); //verifica o formato para salvar no arquivo, se for P3 usa as matriz
        w.write("# Created by Marcos\n");// RED GREEN BLUE que a cada tres valores formam um pixel colorido
        w.write(x+" "+ y + "\n");
        w.write("255"+"\n");
        for(int i=0;i<x;i++){
            for(int j=0;j<y;j++){
               w.write(RED[i][j]+" ");
               w.write(GREEN[i][j]+" ");
               w.write(BLUE[i][j]+" ");
           }
           w.write("\n"); 
        }        
        w.close();       
      }
      
    } 
    
    public int binarizacao(String imagem, int valor) throws IOException{
        //Funcão que faz imagem ficar com duas cores, preto e branco, a partir do valor inserido 
        //pelo o usuário
        short i,j;
       
       le(imagem); // le a imagem
       if (formato.equals("P2")){ //Se ela for em tom de cinza realiza o processamento
        for(i=0;i<x;i++){
             for(j=0;j<y;j++) //percorre a matriz inteira pixel a pixel.
                 if (matriz[i][j]<valor) // se o valor passado pelo o usuário for maior que o 
                     matriz[i][j]=0;//pixel atual da matriz então ele poe 0
                 else
                     matriz[i][j]=255;//Senão ele poe 255
        }
        return 1;
       }
        else {
             JOptionPane.showMessageDialog(null,"Abra uma imagem PGM","Aviso",JOptionPane.WARNING_MESSAGE);
             return 0;
        }
    }
    
    public void RGBtoPGM (String imagem) throws IOException{
        //Funcão que pega uma imagem colorida e salva seus 3 canais RGB separadamente em tons de cinza
        
        int i,j,k=0; 
                
        le(imagem);//le a imagem
        
        if (formato.equals("P3")){ //verifica se a imagem é colorida
        formato="P2"; // muda o formato para imagem em tons de cinza para salvar posteriormente
        for(i=0;i<x;i++){
            for(j=0;j<y;j++)
                matriz[i][j]=RED[i][j]; //pega os valores dos pixel do canal R e passa para a matriz
        }
        if(SPGM()==1) // salva a matriz RED em tons de cinza 
              k++;
        for(i=0;i<x;i++){
            for(j=0;j<y;j++)
                matriz[i][j]=GREEN[i][j];//pega os valores dos pixel do canal G e passa para a matriz
        }
        if(SPGM()==1) // salva a matriz GREEN em tons de cinza 
              k++;
        
        for(i=0;i<x;i++){
            for(j=0;j<y;j++)
                matriz[i][j]=BLUE[i][j]; //pega os valores dos pixel do canal B e passa para a matriz
        }
        if(SPGM()==1) // salva a matriz BLUE em tons de cinza 
              k++;    // fazendo isso ele extrai os tres canais da imagem colorida e passa cada canal
                      //para tons de cinza salvando os separadamente 
        if (k!=0)
               JOptionPane.showMessageDialog(null,"Processamento concluído!", "Processmanto", JOptionPane.PLAIN_MESSAGE);
        }
         else 
             JOptionPane.showMessageDialog(null,"Abra uma imagem PPM","Aviso",JOptionPane.WARNING_MESSAGE);
             
        
    }
    
    public int menosVer(String imagem, int valor) throws IOException{
        //Função que mexe nos valores dos pixels do canal vermelho alterando a influencia de desse canal
        //na imagem colorida. Essa alteração é feita a partir do valor inserido pelo o usuário
        int aux,i,j;
        
        le(imagem);//lê a imagem 
        if (formato.equals("P3")){ //verifica se é colorida para proseguir com o processamento
        for (i=0; i < x; i++){
		for (j=0 ; j < y; j++ ){
			aux = RED[i][j] - valor;//pega o valor que o usuario passou e subtrai
			if ( aux < 0 )          //do valor do pixel da matriz RED e passa para um aux
                            RED[i][j] = 0;      //se o valor do aux for menor que 0 então atribui zero 
                        else                    //para o pixel da matriz RED senão atribui o valor do aux
                            RED[i][j] = aux;    //Isso é feito para evitar que tenha valor de pixel negativo

		}
	}
        return 1;
        }
        else {
            JOptionPane.showMessageDialog(null,"Abra uma imagem PPM","Aviso",JOptionPane.WARNING_MESSAGE);
            return 0;
       }
    }

    public int maisAzul(String imagem, int valor) throws IOException{
    //Função que mexe nos valores dos pixels do canal azul alterando a influencia de desse canal
    //na imagem colorida. Essa alteração é feita a partir do valor inserido pelo o usuário 
        
        int aux,i,j;

    le(imagem);//lê a imagem 
    if (formato.equals("P3")){ //verifica se é colorida para proseguir com o processamento
    for (i=0; i < x; i++){
		for (j=0 ; j < y; j++ ){
			aux = BLUE[i][j] + valor;//pega o valor que o usuario passou e soma
			if ( aux > 255 )         //com valor do pixel da matriz BLUE e passa para um aux
				BLUE[i][j] = 255;//se o valor do aux for maior que 255 então atribui 255
			else                    //para o pixel da matriz BLUE senão atribui o valor do aux
                               BLUE[i][j] = aux;//Isso é feito para evitar que tenha valor de pixel maior que 255

		     }
	}
        return 1;
        }
       else {
            JOptionPane.showMessageDialog(null,"Abra uma imagem PPM","Aviso",JOptionPane.WARNING_MESSAGE);
            return 0;
       }
    }
    
    public void umCanal (String nome) throws IOException{
        //função que a partir de uma imagem colorida extrai os 3 canais RED GREEN BLUE 
        //e salva cada um eles separadamente 
        
        int i,j,k=0; int[][] aux = new int[1000][1000];
        
        le(nome);//lê a imagem 
        if (formato.equals("P3")){ //verifica se a imagem é colorida
        for(i=0;i<x;i++){
           for(j=0;j<y;j++){
             matriz[i][j]=GREEN[i][j]; //Esses 2 for estão pegando pegando os valores dos pixels 
             aux[i][j]=BLUE[i][j];     //das matriz GREEN e BLUE e estão copiando para a matriz e aux, respectivamente
           }                           //isso é necessário porque posteriormente serão atribuidos valores 0 para 
        }                              //as duas matriz afim de extrair somente um canal por vez.
        
         for(i=0;i<x;i++){
           for(j=0;j<y;j++){
             GREEN[i][j] = 0;      //Esses 2 for estão zerando as matrizes GREEN BLUE para ficar 
             BLUE[i][j] = 0;       //somente com a matriz RED sendo assim extraindo ela da imagem colorida
           }
        }
         
        if(SPPM()==1)
              k++;         //salva o canal RED
         
         for(i=0;i<x;i++){
           for(j=0;j<y;j++){
             RED[i][j] = 0;      //aqui os valores da matriz RED são zerados afim de ficar só com os 
             GREEN[i][j] = matriz[i][j]; //valores da matriz GREEN que são copiados da matriz de novo porque no inicio
           }                            //foram zerados 
        }
         
        if(SPPM()==1)
              k++;     //salva o canal GREEN
        
        for(i=0;i<x;i++){
           for(j=0;j<y;j++){
             GREEN[i][j] = 0;     //aqui os valores da matriz GREEN são zerados afim de ficar só com os
             BLUE[i][j] = aux[i][j]; //valores da matriz BLUE que são copiados novamente do aux
           }                        //porque foram zerados no inicio da função
        }
         
        if(SPPM()==1)
              k++; //salva o canal BLUE
        if (k!=0)
               JOptionPane.showMessageDialog(null,"Processamento concluído!", "Processmanto", JOptionPane.PLAIN_MESSAGE);
        }         
         else 
             JOptionPane.showMessageDialog(null,"Abra uma imagem PPM","Aviso",JOptionPane.WARNING_MESSAGE);
    }

    public int destaca2(String imagem,int valor) throws IOException{
        //Funçao que a partir de uma imagem em tons de cinza destaca em duas cores, de acordo com o
        //valor inserido pelo o usuário, alguma região da imagem (Pseudocor)
        int i,j;
        
        le(imagem); //lê a imagem 
        if (formato.equals("P2")){ //verifica se ela é em tons de cinza para continuar o processamento
         for(i=0;i<x;i++){
            for(j=0;j<y;j++){
             if (matriz[i][j]<valor){ //apartir de o valor que o usuário inseriu
                 RED[i][j]=0;         //faz uma comparação com cada valor da matriz 
                 GREEN[i][j]=0;       // se o valor for maior, ele atribui o valor da cor azul
                 BLUE[i][j]=255;      // para o pixel da nova matriz colorida que será gerada 
             }
             else{
                 RED[i][j]=255;      //se o valor inserido for menor ele atribui o valor da cor  
                 GREEN[i][j]=255;    //amarelo para o pixel da nova matriz colorida que será gerada 
                 BLUE[i][j]=0;
             }
           }
        }
       formato="P3";       //troca o formato de tons de cinza para colorido para salvar 
       return 1;
       }
       else {
        JOptionPane.showMessageDialog(null,"Abra uma imagem PGM","Aviso",JOptionPane.WARNING_MESSAGE);
           return 0;
       }
    }
       
    public int geraColorida(String imagem1, String imagem2, String imagem3) throws IOException{
    //Função que a partir de três imagens em tons de cinza (Canais R, G e B) gera uma imagem colorida  
        
        int i,j,k=0;

    le(imagem1); //lê imagem 
    if (formato.equals("P2")){ //verifica se é em tons de cinza para continuar o processamento 
    
    for(i=0;i<x;i++){
       for(j=0;j<y;j++)
           RED[i][j] = matriz[i][j]; //lê os valores da matriz e copiam para a matriz RED que será 
                                     //a parte R da imagem colorida 
     }
    }
    else k++; //variavel que incrementa se não entrou no if
    
    le(imagem2);
    if (formato.equals("P2")){ //verifica se é em tons de cinza para continuar o processamento 
      for(i=0;i<x;i++){
         for(j=0;j<y;j++)
           GREEN[i][j] = matriz[i][j];  //lê os valores da matriz e copiam para a matriz GREEN que será
                                        //a parte G da imagem colorida 
     }
    }
    else k++;  //variavel que incrementa se não entrou no if
    
    le(imagem3);
    if (formato.equals("P2")){ //verifica se é em tons de cinza para continuar o processamento 
    for(i=0;i<x;i++){
       for(j=0;j<y;j++)
           BLUE[i][j] = matriz[i][j]; //lê os valores da matriz e copiam para a matriz BLUE que será
                                      //a parte B da imagem colorida 
    }
    }
    else k++; //variavel que incrementa se não entrou no if
    formato="P3";    //altera o formato para colorido para salvar 
   
    if(k!=0) { //se não entrou em algum if o processamento não é feito
        JOptionPane.showMessageDialog(null,"Abra 3 imagens PGM","Aviso",JOptionPane.WARNING_MESSAGE);
        return 0;
    }
    return 1;
   }
    
    public int alongContraste(String imagem) throws IOException{
    //Função que a partir de uma imagem com o histograma muito concentrado em alguma parte 
    // destribui o histograma por todo o gráfico
    int i,j,A,B,temp; float s;

    le(imagem); //lê imagem 
    if (formato.equals("P2")){ //verifica se a imagem é em tons de cinza para continuar o processamento
    A=B=matriz[0][0]; //pega o primeiro valor de pixel da matriz e atribui para as variaveis A e B
    
    for(i=0;i<x;i++){
       for(j=0;j<y;j++){
          if (matriz[i][j] > B)
              B = matriz[i][j];
          else if (matriz[i][j] < A)
              A = matriz[i][j];
        }
    }// acha menor e maior tom de cinza na imagem    
        
    for(i=0;i<x;i++){
       for(j=0;j<y;j++){
          s = (matriz[i][j] - A)*(255/(B-A)); //função que destribui os pixels por toda a parte do histograma
          if (Math.ceil(s) > 255) // se der uma valor maior que 255 então o valor do pixel fica 255
            matriz[i][j] = 255;
          else if (Math.ceil(s) < 0) // se der uma valor menor que 0 então o valor do pixel fica 0
            matriz[i][j] = 0;
          else matriz[i][j] = (int)Math.ceil(s);
        }
    }
    return 1;
    }
    else {
        JOptionPane.showMessageDialog(null,"Abra uma imagem PGM","Aviso",JOptionPane.WARNING_MESSAGE);
        return 0;
    }
}
    
    public int Gamma (String imagem, double gamma) throws IOException{
    //Função que faz a correção gamma de acordo com o valor inserido pelo o usuário. Escurece ou clareia a imagem
    int i,j; double aux, s;

    le(imagem); //lê imagem 
    if (formato.equals("P2")){ //verifica se a imagem é em tons de cinza para continuar o processamento
    for(i=0;i<x;i++){
       for(j=0;j<y;j++){
               aux = (double)matriz[i][j]/255; //converte os valores dos pixels da matriz para uma escala para ser usada na função gamma
               s = Math.pow(aux,gamma);   //função gamma
               matriz[i][j] = (int)Math.ceil(s*255); //volta para [0,255] para ser possivel visualizar a imagem depois
          }
       }
    return 1;
    }
    else {
        JOptionPane.showMessageDialog(null,"Abra uma imagem PGM","Aviso",JOptionPane.WARNING_MESSAGE);
        return 0;
    }
}
        
    public void CMY (String R,String G,String B) throws IOException{
   
    //Função que a partir de 3 imagens em tons de cinza R, G e B converte para o sistema CMY   
    int i,j,k=0;

    le(R); //lê imagem 
    if (formato.equals("P2")){ //verifica se a imagem é em tons de cinza para continuar o processamento
    for(i=0;i<x;i++){
       for(j=0;j<y;j++)
           matriz[i][j] = 255 - matriz[i][j]; //extrai o canal RED para formar o canal C(Cyan) sem vermelho

    }
    if (SPGM()==1) //salva o canal C
        k++;
    }     
    else JOptionPane.showMessageDialog(null,"Abra uma imagem PGM","Aviso",JOptionPane.WARNING_MESSAGE);
             
        
    le(G); //lê imagem 
    if (formato.equals("P2")){ //verifica se a imagem é em tons de cinza para continuar o processamento
    for(i=0;i<x;i++){
       for(j=0;j<y;j++)
           matriz[i][j] = 255 - matriz[i][j]; //extrai o canal GREEN para formar o canal M(Magenta) sem verde

    }
    if (SPGM()==1) //salva o canal M
        k++;
    }     
    else JOptionPane.showMessageDialog(null,"Abra uma imagem PGM","Aviso",JOptionPane.WARNING_MESSAGE);

    le(B); //lê imagem 
    if (formato.equals("P2")){ //verifica se a imagem é em tons de cinza para continuar o processamento
    for(i=0;i<x;i++){
       for(j=0;j<y;j++)
           matriz[i][j] = 255 - matriz[i][j]; //extrai o canal BLUE para formar o canal Y(Yellow) sem azul

    }
    if (SPGM()==1) //salva o canal Y
        k++;
    }     
    else JOptionPane.showMessageDialog(null,"Abra uma imagem PGM","Aviso",JOptionPane.WARNING_MESSAGE);
    
    if (k!=0) //se k for 0 então quer dizer que não salvou nenhum canal 
               JOptionPane.showMessageDialog(null,"Processamento concluído!", "Processmanto", JOptionPane.PLAIN_MESSAGE);

}
    
    public void HSI (String R,String G,String B) throws IOException{
    //Função que a partir de 3 imagens em tons de cinza R, G e B converte para o sistema HSI    
        int i,j,k=0; double r,g,b,h,s,temp; 
        int[][] H = new int[1200][1200]; 
        int[][] S = new int[1200][1200]; 
        int[][] I = new int[1200][1200];

     le(R); //lê imagem 
     if (formato.equals("P2")){ //verifica se a imagem é em tons de cinza para continuar o processamento
     for(i=0;i<x;i++){
       for(j=0;j<y;j++)
         RED[i][j] = matriz[i][j]; //pega os valores dos pixels da imagem e passa para a matriz do canal RED

      }   
     k++;
     }
     
     le(G); //lê imagem 
     if (formato.equals("P2")){ //verifica se a imagem é em tons de cinza para continuar o processamento
      for(i=0;i<x;i++){
       for(j=0;j<y;j++)
         GREEN[i][j] = matriz[i][j]; //pega os valores dos pixels da imagem e passa para a matriz do canal GREEN
      }
      k++;
     }
     
    le(B); //lê imagem 
    if (formato.equals("P2")){ //verifica se a imagem é em tons de cinza para continuar o processamento
     for(i=0;i<x;i++){
       for(j=0;j<y;j++)
         BLUE[i][j] = matriz[i][j]; //pega os valores dos pixels da imagem e passa para a matriz do canal BLUE
     }
     k++;
    }
    
 if (k == 3){ //se k 3 quer dizer que entrou nos 3 if e então pode continuar o processamento
 for(i=0;i<x;i++){
       for(j=0;j<y;j++){
           r =  (double)RED[i][j]/(RED[i][j]+GREEN[i][j]+BLUE[i][j]); //as 3 fórmulas que calculam os valores de r g e b
           g =  (double)GREEN[i][j]/(RED[i][j]+GREEN[i][j]+BLUE[i][j]); //a partir dos valores de cada pixel das matrizes RED GREEN e BLUE
           b =  (double)BLUE[i][j]/(RED[i][j]+GREEN[i][j]+BLUE[i][j]); //(canais (RGB)

           if(b<=g) // se b for <= g então usa a fórmula abaixo para calcular h
            h = Math.acos(( 0.5*( (r-g) + (r-b) ) ) /  ( Math.pow( ( Math.pow(r-g,2)+(r-b)*(g-b) ),0.5) ) );
           
           else{ // senão for usa a fórmula abaixo para calcular h
            h = (2*3.14) - (Math.acos( ( 0.5*((r-g)+(r-b)) ) /  Math.pow( (Math.pow(r-g,2)+(r-b)*(g-b)),0.5)   ));
           }
           
            H[i][j] = (int) Math.ceil((h*255)/(2*3.14));  //conversão dos valores de h para salvar na matriz H          
            I[i][j] = (int)(RED[i][j]+GREEN[i][j]+BLUE[i][j])/3 ; //média dos valores de cada pixels das matrizes RED GREEN e BLUE 
                                                                   //para salvar na matriz I
            if (r < g && r < b)
                temp = r;
            else if (g < r && g < b)
                temp = g;
            else temp = b; // acha o menor dos 3 valores r g e b

            s = 1-3*(temp); //usa o menor valor para culcular o s 
            s = s*255;//converte valore de s para salvar na matriz S
            S[i][j]=(int)s;
       }
    }
    k = 0;
     for(i=0;i<x;i++){
       for(j=0;j<y;j++)
         matriz[i][j]=H[i][j]; //copia valores da matriz H para a matriz para salvar depois
     }
 
     if(SPGM()==1) //salva H
        k++; 
     
      for(i=0;i<x;i++){
       for(j=0;j<y;j++)
         matriz[i][j]=S[i][j]; //copia valores da matriz S para a matriz para salvar depois
     }
      
     if(SPGM()==1) //salva S
       k++;
     
      for(i=0;i<x;i++){
       for(j=0;j<y;j++)
         matriz[i][j]=I[i][j]; //copia valores da matriz I para a matriz para salvar depois
     }
     if(SPGM()==1) //salva I
     k++;
     
      if (k!=0) 
         JOptionPane.showMessageDialog(null,"Processamento concluído!", "Processmanto", JOptionPane.PLAIN_MESSAGE);
     }
    else  JOptionPane.showMessageDialog(null,"Abra 3 imagens PGM","Aviso",JOptionPane.WARNING_MESSAGE);
             
        
 }
       
    public int SPGM () throws IOException{
       //Funcão usada para salvar pgm usada por alguns metodos acima
        
            NewJFrame im = new NewJFrame();
            File fileS = null; String nomes; int returnVal;
            returnVal = im.SalvaPGM.showSaveDialog(im);  
                                        
        if (returnVal == JFileChooser.APPROVE_OPTION){
                 fileS = im.SalvaPGM.getSelectedFile();
                 nomes = fileS.getAbsolutePath();
                 escreve(nomes + ".pgm");
                 return 1;     //se o usuário salvou a imagem returna 1 
                } 
        else return 0;  //se o usuário cancelou o salvamento da imagem retorna 0
       }
 
    public int SPPM () throws IOException{
        //Funcão usada para salvar ppm usada por alguns metodos acima
            NewJFrame im = new NewJFrame();
            File fileS = null; String nomes; int returnVal;
            returnVal = im.SalvaPPM.showSaveDialog(im);  
                                        
        if (returnVal == JFileChooser.APPROVE_OPTION){
                 fileS = im.SalvaPPM.getSelectedFile();
                 nomes = fileS.getAbsolutePath();
                 escreve(nomes + ".ppm"); //se o usuário salvou a imagem returna 1 
                 return 1;
                } 
        else return 0; //se o usuário cancelou o salvamento da imagem retorna 0
       }
}

    
  
    