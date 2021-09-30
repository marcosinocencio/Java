/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marcosinocencio.cursomc.services;

import com.marcosinocencio.cursomc.security.UserSS;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Vinicius
 */
public class UserService {
    
    public static UserSS authenticated(){
        try{
            return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        catch(Exception e){
            return null;
        }
    } 
}
