/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processamento;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import org.opencv.core.Core;
import static org.opencv.core.Core.BORDER_DEFAULT;
import static org.opencv.core.Core.addWeighted;
import static org.opencv.core.Core.convertScaleAbs;
import static org.opencv.core.CvType.CV_16S;
import static org.opencv.core.CvType.CV_8UC1;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import tela.ImagemOriginal;
import tela.Quadro;

/**
 *
 * @author Vinicius
 */
public class Processamento {
   
    private final Quadro q;
    private final int x, y;
    private final int[][][] canais;
            
    public Processamento(Quadro quadro){
        this.q = quadro;
        x = q.getImagem().getWidth();
        y = q.getImagem().getHeight();
        canais = new int[15][x][y];        
    }
    
    public void rgb() throws IOException{
               
           for(int i = 0; i < q.getImagem().getWidth(); i ++)
                for(int j = 0; j < q.getImagem().getHeight(); j ++){                                
                    Color cor = new Color(q.getImagem().getRGB(i, j));           
                    
                    canais[0][i][j] = cor.getRed();
                    canais[1][i][j] = cor.getGreen();
                    canais[2][i][j] = cor.getBlue();
                }          
    }  
            
    public void sobel(ArrayList<Integer> canaisSelecionados, String tecnicaSelecionada, Quadro qua){
        
        int cSobel[][][] = new int[canaisSelecionados.size()][qua.getImagem().getWidth()][qua.getImagem().getHeight()];       
        int mascara[][] = new int[3][3];    
        int valor;     
        
        
        for(int k = 0; k < canaisSelecionados.size(); k ++)
            for(int i = 1; i < qua.getImagem().getWidth()-1; i ++)
               for(int j = 1; j < qua.getImagem().getHeight()-1; j ++)  {  
                   mascara[0][0] = canais[canaisSelecionados.get(k)][i-1][j-1];
                   mascara[0][1] = canais[canaisSelecionados.get(k)][i-1][j];
                   mascara[0][2] = canais[canaisSelecionados.get(k)][i-1][j+1];
                   mascara[1][0] = canais[canaisSelecionados.get(k)][i][j-1];                     
                   mascara[1][2] = canais[canaisSelecionados.get(k)][i][j+1];
                   mascara[2][0] = canais[canaisSelecionados.get(k)][i+1][j-1];
                   mascara[2][1] = canais[canaisSelecionados.get(k)][i+1][j];       
                   mascara[2][2] = canais[canaisSelecionados.get(k)][i+1][j+1];

                   valor = (int)convolucaoSobel(mascara);              
                   valor = normaliza(valor);      
                   
                   cSobel[k][i][j] = valor;                   
           }
          
           tecnica(tecnicaSelecionada, cSobel, qua, true, null);          
           
    }
    
    public void sobelEVC() throws IOException{
        
        int gX[][][] = new int[3][q.getImagem().getWidth()][q.getImagem().getHeight()];  
        int gY[][][] = new int[3][q.getImagem().getWidth()][q.getImagem().getHeight()];  
        int mascara[][] = new int[3][3];    
//        BufferedImage imGXX = new BufferedImage(q.getImagem().getWidth(),q.getImagem().getHeight(), q.getImagem().getType());   
//        BufferedImage imGYY = new BufferedImage(q.getImagem().getWidth(),q.getImagem().getHeight(), q.getImagem().getType());  
//        BufferedImage imGXY = new BufferedImage(q.getImagem().getWidth(),q.getImagem().getHeight(), q.getImagem().getType());  
        BufferedImage imMAG = new BufferedImage(q.getImagem().getWidth(),q.getImagem().getHeight(), q.getImagem().getType());  
                       
        
        for(int k = 0; k < 3; k ++)
            for(int i = 1; i < q.getImagem().getWidth()-1; i ++)
               for(int j = 1; j < q.getImagem().getHeight()-1; j ++)  {  
                   mascara[0][0] = canais[k][i-1][j-1];
                   mascara[0][1] = canais[k][i-1][j];
                   mascara[0][2] = canais[k][i-1][j+1];
                   mascara[1][0] = canais[k][i][j-1];                     
                   mascara[1][2] = canais[k][i][j+1];
                   mascara[2][0] = canais[k][i+1][j-1];
                   mascara[2][1] = canais[k][i+1][j];       
                   mascara[2][2] = canais[k][i+1][j+1];

                   gX[k][i][j] = normalizaXY(gradienteX(mascara));              
                   gY[k][i][j] = normalizaXY(gradienteY(mascara)); 
                   
               }
          
        double gXX, gYY, gXY, mag, angulo;         
       
          
        for(int i = 1; i < q.getImagem().getWidth()-1; i ++)
             for(int j = 1; j < q.getImagem().getHeight()-1; j ++)  { 

                gXX = Math.abs(gX[0][i][j])*Math.abs(gX[0][i][j]) + Math.abs(gX[1][i][j])*Math.abs(gX[1][i][j]) + Math.abs(gX[2][i][j])*Math.abs(gX[2][i][j]);
                gYY = Math.abs(gY[0][i][j])*Math.abs(gY[0][i][j]) + Math.abs(gY[1][i][j])*Math.abs(gY[1][i][j]) + Math.abs(gY[2][i][j])*Math.abs(gY[2][i][j]);
                gXY = gX[0][i][j]*gY[0][i][j] + gX[1][i][j]*gY[1][i][j] + gX[2][i][j]*gY[2][i][j];                    
//                angulo = (double)0.5*Math.atan(2*gXY/(gXX-gYY));
//                mag = Math.sqrt( 0.5 *( (gXX + gYY) +  (gXX - gYY) * Math.cos(2*angulo)  + 2*gXY*Math.sin(2*angulo)    )  );


                gXX = (gXX/195075)*255;
                gYY = (gYY/195075)*255;
                gXY = (gXY/195075)*255;   
                gXY = (int) gXY;
                gYY = (int) gYY;
                gXX = (int) gXX;
                
                
                if (gXY == 0)
                    gXY = 255;
                if(gXY > 53 && gXY < 75 ){                    
                    gXY = 0;
                }
                
                
                if (gXX == 0)
                    gXX = 255;
                if(gXX > 53 && gXX < 75 ){                    
                    gXX = 0;
                }
                
                
                if (gYY == 0)
                    gYY = 255;
                if(gYY > 53 && gYY < 75 ){                    
                    gYY = 0;
                }
                
                mag = Math.sqrt(gXX*gXX + gYY*gYY + gXY*gXY);
                if( mag > 255)
                    mag = 255;               
                   
                imMAG.setRGB(i, j, new Color((int)mag,(int)mag,(int)mag).getRGB()); 
//                imGXX.setRGB(i, j, new Color((int)gXX,(int)gXX,(int)gXX).getRGB());
//                imGYY.setRGB(i, j, new Color((int)gYY,(int)gYY,(int)gYY).getRGB());
//                imGXY.setRGB(i, j, new Color((int)gXY,(int)gXY,(int)gXY).getRGB());
             }      
        
        
//        Quadro qq = new Quadro(imGXY,"Gxy (Espaço Vetorial de Cores)",null);   
//        ImagemOriginal mostra = new ImagemOriginal(qq);
//        mostra.colocaImagem();
//        
//        qq = new Quadro(imGXX,"Gxx (Espaço Vetorial de Cores)",null);   
//        mostra = new ImagemOriginal(qq);
//        mostra.colocaImagem();
//        
//        qq = new Quadro(imGYY,"Gyy (Espaço Vetorial de Cores)",null);   
//        mostra = new ImagemOriginal(qq);
//        mostra.colocaImagem();
        
        Quadro qq = new Quadro(imMAG,"Espaço Vetorial de Cores",null);   
        ImagemOriginal mostra = new ImagemOriginal(qq);
        mostra.colocaImagem();
    }
    
    public void sobelOpenCV(Quadro quadro) throws IOException{        
        
        Mat src, src_gray = new Mat();
        Mat grad = new Mat();
        int scale = 1;
        int delta = 0;
        int ddepth = CV_16S;
        
        src = bufferedImage2Mat(q.getImagem());       
        
        Imgproc.GaussianBlur(src, src_gray, new Size(3,3), 0, 0, BORDER_DEFAULT);
        
        Imgproc.cvtColor(src, src_gray, Imgproc.COLOR_BGR2GRAY);      

        Mat grad_x = new Mat(), grad_y = new Mat(),
        abs_grad_x = new Mat(), abs_grad_y = new Mat();        
        
        /// Gradient X
        Imgproc.Sobel( src_gray, grad_x, ddepth, 1, 0, 3, scale, delta, BORDER_DEFAULT );
        convertScaleAbs( grad_x, abs_grad_x );
        
        /// Gradient Y
        Imgproc.Sobel( src_gray, grad_y, ddepth, 0, 1, 3, scale, delta, BORDER_DEFAULT );
        convertScaleAbs( grad_y, abs_grad_y );        
         
        addWeighted( abs_grad_x, 0.5, abs_grad_y, 0.5, 0, grad );
        
        quadro.setImagem(mat2BufferedImage(grad));      
        
        colocarImagemQuadro(quadro, " - Sobel OpenCV");    
        
    }
    
    public void tecnica(String tecnicaSelecionada, int[][][] canaisS, Quadro qua, boolean equalizacao, int[][] cEq){
        
        switch(tecnicaSelecionada){
           
            
            case "And": and(canaisS, qua, equalizacao, cEq);
                        break;
                        
            case "Colorido": tresCanais(canaisS, qua, equalizacao, cEq);
                             break;
        
            case "Magnitude": magnitude(canaisS, qua, equalizacao, cEq);
                              break;
        
            case "Máximo": maximo(canaisS, qua, equalizacao, cEq);
                           break;
        
            case "Média": media(canaisS, qua, equalizacao, cEq);    
                          break;
            
            case "Mínimo": minimo(canaisS, qua, equalizacao, cEq);                            
                           break;
                           
            case "Or": or(canaisS, qua, equalizacao, cEq);
                       break;
        
            case "Xor": xor(canaisS, qua, equalizacao, cEq);
                        break;
        
            default:    
                     break;
            
        }
    
    }
    
    public double convolucao3por3 (int[][] mascara){
            double pixel; 
           
            pixel =        (mascara[0][0]/9)  +  (mascara[0][1]/9)   + (mascara[0][2]/9)   +                       
                           (mascara[1][0]/9) + (mascara[1][1]/9) + (mascara[1][2]/9)  +
                           (mascara[2][0]/9) + (mascara[2][1]/9) + (mascara[2][2]/9) ;
            return pixel;
    } 
    
    public double convolucaoSobel (int[][] mascara){

       int gy=(mascara[0][0]*-1)+(mascara[0][1]*-2)+(mascara[0][2]*-1)+(mascara[2][0])+(mascara[2][1]*2)+(mascara[2][2]*1);
       int gx=(mascara[0][0])+(mascara[0][2]*-1)+(mascara[1][0]*2)+(mascara[1][2]*-2)+(mascara[2][0])+(mascara[2][2]*-1);
       return Math.sqrt(gy*gy + gx*gx);
       
    } 
    
    public double gradienteX (int[][] mascara){                     
      int gx=(mascara[0][0])+(mascara[0][2]*-1)+(mascara[1][0]*2)+(mascara[1][2]*-2)+(mascara[2][0])+(mascara[2][2]*-1);
      return gx;       
    } 

    public double gradienteY (int[][] mascara){
      int gy=(mascara[0][0]*-1)+(mascara[0][1]*-2)+(mascara[0][2]*-1)+(mascara[2][0])+(mascara[2][1]*2)+(mascara[2][2]*1);
      return gy;       
    } 
     
    public int normaliza(double valor){        
        double aux;
        aux = (valor/1442)*255;        
        return (int)aux;
    }
    
    public int normalizaXY(double valor){        
        double aux;
        aux = ((valor+1020)/(2*1020))*255;        
        return (int)aux;
    }
    
    public void yiq(){
        
        double y2,i,q2;
        
        for(int k = 0; k < q.getImagem().getWidth(); k ++)
                for(int j = 0; j < q.getImagem().getHeight(); j ++){                                
                    Color cor = new Color(q.getImagem().getRGB(k, j));
                    
                    double r,g,b;
                    
                    r = (double)cor.getRed()/255;
                    g = (double)cor.getGreen()/255;
                    b = (double)cor.getBlue()/255;
                         
                    y2 = 0.299*r + 0.587*g + 0.114*b;
                    i = 0.596*r - 0.274*g - 0.322*b;
                    q2 = 0.211*r - 0.523*g + 0.312*b;
                   
                    
                    //normalizar para visualizar
                    //(maxAllowed - minAllowed) * (unscaledNum - min) / (max - min) + minAllowed;
                    
                    y2 = y2*255;
                    i =  255*(i+0.5957)/(2*0.5957);
                    q2 =  255*(q2+0.5226) / (2*0.5226);       
                    
                    canais[3][k][j] = (int) y2;
                    canais[4][k][j] = (int) i;
                    canais[5][k][j] = (int) q2;                    
                  
                }        
    
    }
    
    public void yiq2RGB(int[][] c1, int[][] c2, int[][] c3){
        
        double r,g,b;
        double y2,i,q2;
        
        for(int k = 0; k < q.getImagem().getWidth(); k ++)
                for(int j = 0; j < q.getImagem().getHeight(); j ++){          
                    //(maxAllowed - minAllowed) * (unscaledNum - min) / (max - min) + minAllowed;
                    y2 =  (double) c1[k][j] / 255;
                    i  = (double) (2*0.5957) * (c2[k][j]) / 255 - 0.5957;
                    q2  = (double)(2*0.5226) * (c3[k][j]) / 255 - 0.5226;
                    
                                     
                    r = y2 + 0.956*i + 0.621*q2;
                    g = y2 - 0.272*i - 0.647*q2;
                    b = y2 - 1.106*i + 1.703*q2;                   
                    
                    //normalizar 
                    
                    r = (r*255);
                    g = 255*(g+0.5957)/(2*0.5957);      
                    b = 255*(b+0.5226) / (2*0.5226);
                   
                    if(r > 255)
                        r = 255; 
                    if(r < 0)
                        r = 0;
                    
                    if(g > 255)
                        g = 255;
                    if(g < 0)
                        g = 0;
                    
                    if(b > 255)
                        b = 255;
                    if(b < 0)
                        b = 0;
                    
                    c1[k][j] = (int) r;                          
                    c2[k][j] = (int) g;
                    c3[k][j] = (int) b;  
                   
                }        
    
    }
    
    public void hsv(){
       
        double h, s, v, r, g, b;
        
        for(int k = 0; k < q.getImagem().getWidth(); k ++)
                for(int j = 0; j < q.getImagem().getHeight(); j ++){                                
                    Color cor = new Color(q.getImagem().getRGB(k, j));
                    
                    r = (double)cor.getRed()/255;
                    g = (double)cor.getGreen()/255;
                    b = (double)cor.getBlue()/255;
                                   
                    double max, min;                                           
                   
                    max = Math.max(r, Math.max(g,b));
                    min = Math.min(r, Math.min(g,b));
                    h = 0;
                    
                    if(max == r && g >= b){
                        h = 60 * ((g-b)/(max-min));
                    }
                    else if(max == r && g < b){
                        h = 60 * ((g-b)/(max-min)) + 360;
                    }
                    else if(max == g){
                        h = 60 * ((b-r)/(max-min)) + 120;
                    }
                    else if(max == b){
                        h = 60 * ((r-g)/(max-min)) + 240;    
                    }
                   
                    //normalizar
                    h = (h/420)*255;                    
                    s = ((max - min)/max)*255;
                    v = max*255;
                    
                    canais[6][k][j] = (int) h;
                    canais[7][k][j] = (int) s;
                    canais[8][k][j] = (int) v;  
                }    
    
    }
    
    public void YCbCr (){
        
        double y,cb,cr;
        for(int k = 0; k < q.getImagem().getWidth(); k ++)
                for(int j = 0; j < q.getImagem().getHeight(); j ++){                                
                    Color cor = new Color(q.getImagem().getRGB(k, j));

                    y = (0.299*cor.getRed())+(0.587*cor.getGreen())+(0.114*cor.getBlue());
                    cb = 128-(0.168736*cor.getRed())-(0.331264*cor.getGreen())+(0.5*cor.getBlue());
                    cr =  128+(0.5*cor.getRed())-(0.418688*cor.getGreen())-(0.081312*cor.getBlue());
                

                    canais[12][k][j] =  (int)y;
                    canais[13][k][j] =  (int)cb;
                    canais[14][k][j] =  (int)cr;  
                }    
    
    }
    
    public void cmy (){
    
        
        int c,m,y;
        for(int k = 0; k < q.getImagem().getWidth(); k ++)
                for(int j = 0; j < q.getImagem().getHeight(); j ++){                                
                    Color cor = new Color(q.getImagem().getRGB(k, j));


                    c = 255 - cor.getRed();
                    m = 255 - cor.getGreen();
                    y = 255 - cor.getBlue(); 
                

                    canais[9][k][j] =  c;
                    canais[10][k][j] =  m;
                    canais[11][k][j] =  y;  
                }              
           
    }
    
    public void calculaCanais() throws IOException{
        rgb();
        yiq();
        hsv();
        cmy(); 
        YCbCr();
    }
   
    public void tresCanais(int[][][] canaisS, Quadro qua, boolean equalizacao, int[][] cEq){   
        
        Random gerador = new Random();
        for(int k = 0; k < q.getImagem().getWidth(); k ++)
                for(int j = 0; j < q.getImagem().getHeight(); j ++){                                
                    int red = canaisS[gerador.nextInt(canaisS.length)][k][j];
                    int green = canaisS[gerador.nextInt(canaisS.length)][k][j];
                    int blue = canaisS[gerador.nextInt(canaisS.length)][k][j];                 
                    
                    if(equalizacao)
                         qua.getImagem().setRGB(k, j, new Color(red,green,blue).getRGB());
                    else
                        cEq[k][j] = red;
                }       
         if(equalizacao)
             colocarImagemQuadro(qua," (Colorido)");
    }
    
    public void magnitude(int[][][] canaisS, Quadro qua, boolean equalizacao, int[][] cEq){   
        
        int valor;
        for(int k = 0; k < q.getImagem().getWidth(); k ++)
                for(int j = 0; j < q.getImagem().getHeight(); j ++){                                
                    valor = 0;              
                    
                    for(int i = 0; i < canaisS.length; i++)  
                        valor += canaisS[i][k][j]*canaisS[i][k][j]; 
                        
                    double mag = (int) Math.sqrt(valor);
                    mag = (mag/883)*255;
                    
                    if(equalizacao)
                        qua.getImagem().setRGB(k, j,  new Color((int)mag,(int)mag,(int)mag).getRGB());
                    else 
                        cEq[k][j] = (int)mag;
                }      
        if( equalizacao)  
            colocarImagemQuadro(qua, " (Magnitude)");
    }
     
    public void media(int[][][] canaisS, Quadro qua, boolean equalizacao, int[][] cEq){   
        
        int valor;
        for(int k = 0; k < q.getImagem().getWidth(); k ++)
            for(int j = 0; j < q.getImagem().getHeight(); j ++){                                
                valor = 0;              

                for(int i = 0; i < canaisS.length; i++)  
                    valor += canaisS[i][k][j];                 

                double media = valor/canaisS.length;

                if(equalizacao)
                     qua.getImagem().setRGB(k, j,  new Color((int)media,(int)media,(int)media).getRGB());
                else
                    cEq[k][j] = (int)media;
              }       
          if(equalizacao)
             colocarImagemQuadro(qua, " (Média)");
    }    
    
    public void maximo(int[][][] canaisS, Quadro qua, boolean equalizacao, int[][] cEq){   
        
        int max;
        for(int k = 0; k < qua.getImagem().getWidth(); k ++)
            for(int j = 0; j < qua.getImagem().getHeight(); j ++){                                
               max = 0;
               for(int i = 0; i < canaisS.length; i++)
                   max = Math.max(max, canaisS[i][k][j]);       

                if(equalizacao)
                    qua.getImagem().setRGB(k, j,  new Color(max,max,max).getRGB());
                else
                    cEq[k][j] = max;
            }       
          if(equalizacao)
            colocarImagemQuadro(qua, " (Máximo)");
    }
    
    public void minimo(int[][][] canaisS, Quadro qua, boolean equalizacao, int[][] cEq){   
        
        int min;
        for(int k = 0; k < qua.getImagem().getWidth(); k ++)
            for(int j = 0; j < qua.getImagem().getHeight(); j ++){                                
               min = 255;
               for(int i = 0; i < canaisS.length; i++)
                   min = Math.min(min, canaisS[i][k][j]);       

                if(equalizacao)
                    qua.getImagem().setRGB(k, j,  new Color(min,min,min).getRGB());
                else
                    cEq[k][j] = min;
            }       
          if (equalizacao)
             colocarImagemQuadro(qua, " (Mínimo)");
    }
    
    public void or(int[][][] canaisS, Quadro qua, boolean equalizacao, int[][] cEq){   
        
        int or;
        for(int k = 0; k < qua.getImagem().getWidth(); k ++)
            for(int j = 0; j < qua.getImagem().getHeight(); j ++){                                
               or = canaisS[0][k][j];
               for(int i = 0; i < canaisS.length; i++)
                   or = or | canaisS[i][k][j];       

                if(equalizacao)
                    qua.getImagem().setRGB(k, j,  new Color(or,or,or).getRGB());
                else
                    cEq[k][j] = or;
            }       
          if(equalizacao)
             colocarImagemQuadro(qua, " (Or)");
    }
    
    public void and(int[][][] canaisS, Quadro qua, boolean equalizacao, int[][] cEq){   
        
        int and;
        for(int k = 0; k < qua.getImagem().getWidth(); k ++)
            for(int j = 0; j < qua.getImagem().getHeight(); j ++){                                
               and = canaisS[0][k][j];
               for(int i = 0; i < canaisS.length; i++)
                   and = and & canaisS[i][k][j];       

                if(equalizacao)   
                    qua.getImagem().setRGB(k, j,  new Color(and,and,and).getRGB());
                else
                    cEq[k][j] = and;
            }       
          if(equalizacao)
               colocarImagemQuadro(qua, " (And)");
    }
     
    public void xor(int[][][] canaisS, Quadro qua, boolean equalizacao, int[][] cEq){   
        
        int xor;
        for(int k = 0; k < qua.getImagem().getWidth(); k ++)
            for(int j = 0; j < qua.getImagem().getHeight(); j ++){                                
               xor = canaisS[0][k][j];
               for(int i = 0; i < canaisS.length; i++)
                   xor = xor ^ canaisS[i][k][j];       

                if(equalizacao)
                    qua.getImagem().setRGB(k, j,  new Color(xor,xor,xor).getRGB());
                else
                    cEq[k][j] = xor;
            }       
          if(equalizacao)
             colocarImagemQuadro(qua, " (Xor)");
    }
    
    public void colocarImagemQuadro(Quadro quadro, int canal, String nome){
        
        for(int i = 0;i < q.getImagem().getWidth(); i++)
            for(int j = 0; j <q.getImagem().getHeight(); j++)
                quadro.getImagem().setRGB(i, j, new Color(canais[canal][i][j],canais[canal][i][j],canais[canal][i][j]).getRGB());
        
        quadro.setNome(nome);
        
        ImageIcon k = new ImageIcon(quadro.getImagem());
        Image j = k.getImage().getScaledInstance(quadro.getQuadro().getWidth(), quadro.getQuadro().getHeight(), Image.SCALE_DEFAULT);
        k.setImage(j);
        quadro.getQuadro().setIcon(k);
    }  
    
    public void colocarImagemQuadro(Quadro quadro,  String nome){
                       
        quadro.setNome(quadro.getNome()+nome);
        
        ImageIcon k = new ImageIcon(quadro.getImagem());
        Image j = k.getImage().getScaledInstance(quadro.getQuadro().getWidth(), quadro.getQuadro().getHeight(), Image.SCALE_DEFAULT);
        k.setImage(j);
        quadro.getQuadro().setIcon(k);
    }      
    
    public void diferenca(Quadro qua, Quadro qua2) throws IOException{   
               
        BufferedImage diferenca = new BufferedImage(qua.getImagem().getWidth(),qua.getImagem().getHeight(),qua.getImagem().getType());
        
        int red1, green1, blue1, red2, green2, blue2;
       
        
        for(int i = 0; i < qua.getImagem().getWidth(); i++)
            for(int j = 0; j < qua.getImagem().getHeight(); j++){
                Color cor1 = new Color(qua.getImagem().getRGB(i, j));
                Color cor2 = new Color(qua2.getImagem().getRGB(i, j));
                red1 = cor1.getRed();
                green1 = cor1.getGreen();
                blue1= cor1.getBlue();
                red2 = cor2.getRed();
                green2 = cor2.getGreen();
                blue2 = cor2.getBlue();   
                
                red1 = Math.abs(red1 - red2);
                green1 = Math.abs(green1 - green2);
                blue1 = Math.abs(blue1 - blue2);
                
                diferenca.setRGB(i, j, new Color(red1,green1,blue1).getRGB());
            }
        Quadro q = new Quadro(diferenca," Diferença",null);   
        ImagemOriginal mostra = new ImagemOriginal(q);
        mostra.colocaImagem();
    }
    
    public void otsu() throws IOException{
        
//        Mat mR = bufferedImage2Mat(imagens[0]);
//        Mat mG = bufferedImage2Mat(imagens[1]);
//        Mat mB = bufferedImage2Mat(imagens[2]);
//        
//        ArrayList<Mat> lR = new ArrayList<>(1);
//        ArrayList<Mat> lG = new ArrayList<>(1);
//        ArrayList<Mat> lB = new ArrayList<>(1);
//        Core.split(mR, lR);
//        Core.split(mG, lG);
//        Core.split(mB, lB);
//        mR = lR.get(0);
//        mG = lG.get(0);        
//        mB = lB.get(0);
//        
//        Imgproc.threshold(mR, mR, 0, 255, Imgproc.THRESH_OTSU | Imgproc.THRESH_BINARY);
//        Imgproc.threshold(mG, mG, 0, 255, Imgproc.THRESH_OTSU | Imgproc.THRESH_BINARY);
//        Imgproc.threshold(mB, mB, 0, 255, Imgproc.THRESH_OTSU | Imgproc.THRESH_BINARY);
//        
//        imagens[0] = mat2BufferedImage(mR);
//        imagens[1] = mat2BufferedImage(mG);
//        imagens[2] = mat2BufferedImage(mB);
//        
//        colocarImagemQuadro(0,0);
//        colocarImagemQuadro(1,1);
//        colocarImagemQuadro(2,2);  
//        
    }
    
    public double otsu(Quadro quadro, boolean opcao) throws IOException{
        
        Mat in = bufferedImage2Mat(q.getImagem());
     
        Imgproc.cvtColor(in, in, Imgproc.COLOR_BGR2GRAY);    
        
        double valor = Imgproc.threshold(in, in, 0, 255, Imgproc.THRESH_OTSU | Imgproc.THRESH_BINARY);      
        
        if(opcao){
            quadro.setImagem(mat2BufferedImage(in));      

            colocarImagemQuadro(quadro, " - Otsu OpenCV");     
        }
        return valor;
    }
    
    public void cannyOpenCV(Quadro quadro, float vL, float vH) throws IOException{

       Mat in = bufferedImage2Mat(q.getImagem());       

       Imgproc.cvtColor(in, in, Imgproc.COLOR_BGR2GRAY); 

       Imgproc.GaussianBlur(in, in, new Size(5, 5) ,1 ,1);

       Imgproc.Canny(in, in, vL, vH);       

       quadro.setImagem(mat2BufferedImage(in));   

       colocarImagemQuadro(quadro, " - Canny OpenCV"); 
        
    }
     
    public void cannyOpenCV(Quadro quadro, float lI, float lS, ArrayList<Integer> canaisSelecionados, String tecnicaSelecionada) throws IOException{

       int cSobel[][][] = new int[canaisSelecionados.size()][quadro.getImagem().getWidth()][quadro.getImagem().getHeight()];   

       Mat aux = new Mat(quadro.getImagem().getHeight(), quadro.getImagem().getWidth(), CV_8UC1);


       for(int k = 0; k < canaisSelecionados.size(); k ++){

           for(int i = 0; i < quadro.getImagem().getWidth(); i ++)
              for(int j = 0; j < quadro.getImagem().getHeight(); j ++)   
                 aux.put(j, i, canais[canaisSelecionados.get(k)][i][j]);                   

           Imgproc.GaussianBlur(aux, aux, new Size(5, 5) ,1 ,1);                         
           Imgproc.Canny(aux, aux, lI, lS);
           copiaMatToMaInt(aux,cSobel,k);
       }
       tecnica(tecnicaSelecionada, cSobel, quadro, true, null);   
        
    } 
    
    public void copiaMatToMaInt (Mat a, int [][][] mat, int n ){
    
        for(int i = 0; i < mat[0].length; i ++)
               for(int j = 0; j < mat[0][0].length; j ++)                   
                  mat[n][i][j] = (int)a.get(j, i)[0];
    
    } 
    
    public static Mat bufferedImage2Mat(BufferedImage image) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "bmp", byteArrayOutputStream);
        byteArrayOutputStream.flush();
        return Imgcodecs.imdecode(new MatOfByte(byteArrayOutputStream.toByteArray()), Imgcodecs.CV_LOAD_IMAGE_UNCHANGED);
    }
    
    public static BufferedImage mat2BufferedImage(Mat matrix)throws IOException {
        MatOfByte mob=new MatOfByte();
        Imgcodecs.imencode(".jpg", matrix, mob);
        return ImageIO.read(new ByteArrayInputStream(mob.toArray()));
    }
    
    public void equalizacao(ArrayList<Integer> canaisSelecionados1, ArrayList<Integer> canaisSelecionados2, ArrayList<Integer> canaisSelecionados3, String tecnicaSelecionada, Quadro quadro, boolean aleatoria, String nome, int vez) throws IOException{
        
        int cEqualizacao1[][][] = new int[canaisSelecionados1.size()][quadro.getImagem().getWidth()][quadro.getImagem().getHeight()];    
        int cEqualizacao2[][][] = new int[canaisSelecionados2.size()][quadro.getImagem().getWidth()][quadro.getImagem().getHeight()];    
        int cEqualizacao3[][][] = new int[canaisSelecionados3.size()][quadro.getImagem().getWidth()][quadro.getImagem().getHeight()];   
        int cEJ1[][] = new int[quadro.getImagem().getWidth()][quadro.getImagem().getHeight()];    
        int cEJ2[][] = new int[quadro.getImagem().getWidth()][quadro.getImagem().getHeight()];    
        int cEJ3[][] = new int[quadro.getImagem().getWidth()][quadro.getImagem().getHeight()];  
        int cAux2[][][] = null;
        int cEJaux[][] = null;
        
        ArrayList<Integer> cAux = new ArrayList();
        Mat aux = new Mat(quadro.getImagem().getHeight(), quadro.getImagem().getWidth(), CV_8UC1);
        
        for(int n = 0; n < 3; n++){
           
            switch(n){
                case 0:  cAux = canaisSelecionados1;
                         cAux2 = cEqualizacao1;
                         cEJaux = cEJ1;
                         break;
                         
                case 1:  cAux = canaisSelecionados2;
                         cAux2 = cEqualizacao2;
                         cEJaux = cEJ2;
                         break;         
            
                case 2:  cAux = canaisSelecionados3;
                         cAux2 = cEqualizacao3;
                         cEJaux = cEJ3;
                         break;  
                         
                default:
                        break;
            }
            
            for(int k = 0; k < cAux.size(); k ++){

                for(int i = 0; i < quadro.getImagem().getWidth(); i ++)
                   for(int j = 0; j < quadro.getImagem().getHeight(); j ++)   
                      aux.put(j, i, canais[cAux.get(k)][i][j]);                   

                Imgproc.equalizeHist(aux, aux);
                copiaMatToMaInt(aux,cAux2,k);
            }
            tecnica(tecnicaSelecionada, cAux2, quadro, false, cEJaux); 
        }
        
        BufferedImage a = null;       
        
        if(aleatoria)
             a = new BufferedImage(quadro.getImagem().getWidth(),quadro.getImagem().getHeight(),quadro.getImagem().getType());
        
            
        for(int i = 0; i < quadro.getImagem().getWidth(); i++)
               for(int j = 0; j < quadro.getImagem().getHeight(); j++) {
                   if(aleatoria)
                       a.setRGB(i, j, new Color(cEJ1[i][j], cEJ2[i][j], cEJ3[i][j]).getRGB());
                   else
                       quadro.getImagem().setRGB(i, j, new Color(cEJ1[i][j], cEJ2[i][j], cEJ3[i][j]).getRGB());
               }       
        nome += vez;
        if(aleatoria){
            nome = nomeAleatoria(nome, canaisSelecionados1, canaisSelecionados2, canaisSelecionados3, tecnicaSelecionada);
            ImageIO.write(a, "bmp", new File(nome+".bmp"));
        }
        else
            colocarImagemQuadro(quadro," ("+tecnicaSelecionada+")");
    }
    
    public String nomeAleatoria (String nome, ArrayList<Integer> canaisSelecionados1, ArrayList<Integer> canaisSelecionados2 ,ArrayList<Integer> canaisSelecionados3, String tecnicaSelecionada  ){
        
         int n = 0;
         ArrayList<Integer> aux = new ArrayList();
         
         
         
         for(;n < 3;n++){
             switch(n){
                 case 0: aux = canaisSelecionados1;
                         break;
                 case 1: aux = canaisSelecionados2;
                         break;
                 case 2: aux = canaisSelecionados3;
                         break;
                 default:
                         break;
             
             }
             
             nome +=" #C"+(n+1)+" -";
             
             for(int i = 0;i < aux.size(); i++)
                switch(aux.get(i)){
                    case 0: nome += " R";
                            break;
                    case 1: nome += " G";
                            break;
                    case 2: nome += " B";
                            break;
                    case 3: nome += " Y";
                            break;
                    case 4: nome += " I";
                            break;
                    case 5: nome += " Q";
                            break;
                    case 6: nome += " H";
                            break;
                    case 7: nome += " S";
                            break;
                    case 8: nome += " V";
                            break;
                    case 9: nome += " C";
                            break;
                    case 10: nome += " M";
                             break;
                    case 11: nome += " Y2";
                             break;
                    case 12: nome += " Y3";
                             break;         
                    case 13: nome += " Cb";
                             break;         
                    case 14: nome += " Cr";
                             break;         
                    default:
                            break;
                }
             
           
         }
         
        
        nome += "# ";
        switch(tecnicaSelecionada){
           
            
            case "And": nome += "(And)";
                        break;
                        
            case "Colorido": nome += "(Colorido)";
                             break;
        
            case "Magnitude": nome += "(Magnitude)";
                              break;
        
            case "Máximo": nome += "(Máximo)";
                           break;
        
            case "Média": nome += "(Média)";   
                          break;
            
            case "Mínimo": nome += "(Mínimo)";                           
                           break;
                           
            case "Or": nome += "(Or)";
                       break;
        
            case "Xor": nome += "(Xor)";
                        break;
        
            default:    
                     break;       
        
        }
        return nome;
    }
    
    public void equalizacaoOpenCV(Quadro quadro) throws IOException{
    
        Mat img, img_hist_equalized = new Mat();
       
        img = bufferedImage2Mat(q.getImagem());
    
        Imgproc.cvtColor(img, img_hist_equalized, Imgproc.COLOR_BGR2YCrCb); 
        
        ArrayList<Mat> YCrBr = new ArrayList<>(3);
        Core.split(img_hist_equalized, YCrBr);
        
        Imgproc.equalizeHist(YCrBr.get(0), YCrBr.get(0));
    
        Core.merge(YCrBr, img_hist_equalized);
        
        Imgproc.cvtColor(img_hist_equalized, img_hist_equalized, Imgproc.COLOR_YCrCb2BGR); 
        
        quadro.setImagem(mat2BufferedImage(img_hist_equalized));   
        
        colocarImagemQuadro(quadro, " - Imagem Equalizada OpenCV");
    
    }
    
    public void equalizacaoHSV(Quadro quadro) throws IOException{
     
        Mat img, img_hist_equalized = new Mat();
       
        img = bufferedImage2Mat(q.getImagem());
    
        Imgproc.cvtColor(img, img_hist_equalized, Imgproc.COLOR_BGR2HSV); 
       
        ArrayList<Mat> HSV = new ArrayList<>(3);
        Core.split(img_hist_equalized, HSV);        

        Imgproc.equalizeHist(HSV.get(2), HSV.get(2));
       
        Core.merge(HSV, img_hist_equalized);
        
        Imgproc.cvtColor(img_hist_equalized, img_hist_equalized, Imgproc.COLOR_HSV2BGR); 
        
        quadro.setImagem(mat2BufferedImage(img_hist_equalized)); 
        
        colocarImagemQuadro(quadro, "");
    
    }
    
    public void equalizacaoYIQ(Quadro quadro) throws IOException{
       
        int r[][] = new int[quadro.getImagem().getWidth()][quadro.getImagem().getHeight()];
        int g[][] = new int[quadro.getImagem().getWidth()][quadro.getImagem().getHeight()];
        int b[][] = new int[quadro.getImagem().getWidth()][quadro.getImagem().getHeight()];
        
        Mat aux = new Mat(quadro.getImagem().getWidth(), quadro.getImagem().getHeight(), CV_8UC1);     
        
        
        for(int i = 0; i < quadro.getImagem().getWidth(); i ++)
           for(int j = 0; j < quadro.getImagem().getHeight(); j ++) { 
              aux.put(i, j, canais[3][i][j]);  
              g[i][j] = canais[4][i][j];              
              b[i][j] = canais[5][i][j];       
           }   
        
        Imgproc.equalizeHist(aux, aux);       
      
        for(int i = 0; i < quadro.getImagem().getWidth(); i ++)
               for(int j = 0; j < quadro.getImagem().getHeight(); j ++)                  
                  r[i][j] = (int)aux.get(i, j)[0];
                   
               
        yiq2RGB(r,g,b);       
        
        for(int i = 0; i < quadro.getImagem().getWidth(); i ++)
          for(int j = 0; j < quadro.getImagem().getHeight(); j ++) 
               quadro.getImagem().setRGB(i, j, new Color(r[i][j], g[i][j], b[i][j]).getRGB());
           
        
        colocarImagemQuadro(quadro, "");
    
    }
    
    public void equalizaAleatoriamente(Quadro quadro, int nImagens, String nome) throws IOException{
        
        ArrayList<Integer> canais1 = new ArrayList();
        ArrayList<Integer> canais2 = new ArrayList(); 
        ArrayList<Integer> canais3 = new ArrayList();
        ArrayList<Integer> a1 = new ArrayList();
        ArrayList<Integer> a2 = new ArrayList(); 
        ArrayList<Integer> a3 = new ArrayList();
        ArrayList<String> tecnica = new ArrayList();
        tecnica.addAll(Arrays.asList("And","Colorido","Magnitude","Máximo","Média","Mínimo","Or","Xor"));
        
        for(int i = 0; i < 15  ; i++){
             a1.add(i);
             a2.add(i);
             a3.add(i);       
        }
        
        Collections.shuffle(a1);
        Collections.shuffle(a2);
        Collections.shuffle(a3);
        Collections.shuffle(tecnica);
        
        Random gerador = new Random();
        int n = gerador.nextInt(15);
        if (n == 0)
            n = 1;
        
        for (int i = 0; i < n; i++)
            canais1.add(a1.get(i));
        
        gerador = new Random();
        n = gerador.nextInt(15);
        if (n == 0)
            n = 1;
        
        for (int i = 0; i < n; i++)
            canais2.add(a2.get(i));
        
        gerador = new Random();
        n = gerador.nextInt(15);
        if (n == 0)
            n = 1;
        
        for (int i = 0; i < n; i++)
            canais3.add(a3.get(i));
        
        
        equalizacao(canais1, canais2, canais3, tecnica.get(0), quadro, true, nome, nImagens);
        
    }
}
