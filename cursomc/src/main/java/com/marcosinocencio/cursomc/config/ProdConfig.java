/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marcosinocencio.cursomc.config;

import com.marcosinocencio.cursomc.services.DBService;
import com.marcosinocencio.cursomc.services.EmailService;
import com.marcosinocencio.cursomc.services.SmtpEmailService;
import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 *
 * @author Vinicius
 */
@Configuration
@Profile("prod")
public class DevConfig {
    
    @Autowired
    private DBService dbService;
    
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String strategy;
    
    @Bean
    public boolean instantiateDatabase() throws ParseException{
        
        if(!"create".equals(strategy)){
            return false;
        }
        
        dbService.instantiateDatabase();
        return true;
    }
    
    @Bean
    public EmailService emailService(){
        return new SmtpEmailService();
    }
}
