/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marcosinocencio.cursomc.services;

import com.marcosinocencio.cursomc.domain.Cliente;
import com.marcosinocencio.cursomc.repositories.ClienteRepository;
import com.marcosinocencio.cursomc.services.exceptions.ObjectNotFoundException;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Vinicius
 */
@Service
public class AuthService {
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private BCryptPasswordEncoder pe; 
    
    @Autowired 
    private EmailService emailService;    
    
    private Random rand = new Random();
    
    public void sendNewPassword(String email){
        Cliente cliente = clienteRepository.findByEmail(email);
        if(cliente == null){
            throw new ObjectNotFoundException("Email não encontrado");
        }
        
        String newPass = newPassword();        
        cliente.setSenha(pe.encode(newPass));
        
        clienteRepository.save(cliente);
        
        emailService.sendNewPasswordEmail(cliente, newPass);
    }

    private String newPassword() {
        char[] vet = new char[10];
        for(int i = 0; i<10; i++){
            vet[i] = randomChar();
        }
        return new String(vet);
    }

    private char randomChar() {
        int opt = rand.nextInt(3);
        if(opt == 0){ // digito
            return (char) (rand.nextInt(10) + 48);
        }else if(opt == 1){ // letra maíuscula
            return (char) (rand.nextInt(26) + 65);
        }else{ // letra minúscula
            return (char) (rand.nextInt(26) + 97);
        }
    }
    
}
