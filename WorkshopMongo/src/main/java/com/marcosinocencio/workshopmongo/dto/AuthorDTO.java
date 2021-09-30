/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marcosinocencio.workshopmongo.dto;

import com.marcosinocencio.workshopmongo.domain.User;
import java.io.Serializable;

/**
 *
 * @author Vinicius
 */
public class AuthorDTO implements Serializable{
    private static final long serialVersionUID = 1L;
    private String nome;
    private String id;
    
    public AuthorDTO(){
    }
    
    public AuthorDTO(User obj){
        id = obj.getId();
        nome = obj.getName();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }   
}
