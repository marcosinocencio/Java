/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marcosinocencio.cursomc.config;

import com.marcosinocencio.cursomc.services.DBService;
import com.marcosinocencio.cursomc.services.EmailService;
import com.marcosinocencio.cursomc.services.MockEmailService;
import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 *
 * @author Vinicius
 */
@Configuration
@Profile("test")
public class TestConfig {
    
    @Autowired
    private DBService dbService;
    
    
    @Bean
    public boolean instantiateDatabase() throws ParseException{
        dbService.instantiateDatabase();
        return true;
    }
    
    @Bean
    public EmailService emailService(){
        return new MockEmailService();
    }   
  
}
