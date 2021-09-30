/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marcosinocencio.cursomc.services;

import com.marcosinocencio.cursomc.domain.Estado;
import com.marcosinocencio.cursomc.repositories.EstadoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Vinicius
 */
@Service
public class EstadoService {
    
    @Autowired
    private EstadoRepository repo;
    
    public List<Estado> findAll(){
        return repo.findAllByOrderByNome();
    }
}
