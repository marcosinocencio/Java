/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marcosinocencio.workshopmongo.services.exception;

/**
 *
 * @author Vinicius
 */
public class ObjectNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    
    public ObjectNotFoundException(String msg){
        super(msg);
    }
}
