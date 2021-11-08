/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cursomicroservicos.hruser.resources;

import com.cursomicroservicos.hruser.entities.User;
import com.cursomicroservicos.hruser.repositories.UserRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Vinicius
 */
@RestController
@RequestMapping(value = "/users")
public class UserResource {    
    
    @Autowired
    private UserRepository repository;     
    
    @GetMapping(value = "/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id){       
        User obj = repository.findById(id).get();
        return ResponseEntity.ok(obj);
    }
    
    @GetMapping(value = "/search")
    public ResponseEntity<User> findByEmail(@RequestParam String email){       
        User obj = repository.findByEmail(email);
        return ResponseEntity.ok(obj);
    }
}
