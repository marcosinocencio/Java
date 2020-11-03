/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tela;

import java.awt.image.BufferedImage;
import javax.swing.JLabel;

/**
 *
 * @author Vinicius
 */
public class Quadro {
    
    private String nome;
    private BufferedImage imagem;
    private JLabel quadro;
    
    
    public Quadro(BufferedImage imagem, String nome, JLabel quadro){
        this.imagem = imagem;
        this.nome = nome;
        this.quadro = quadro;    
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BufferedImage getImagem() {
        return imagem;
    }

    public void setImagem(BufferedImage imagem) {
        this.imagem = imagem;
    }

    public JLabel getQuadro() {
        return quadro;
    }

    public void setQuadro(JLabel quadro) {
        this.quadro = quadro;
    }
    
    
    
}
