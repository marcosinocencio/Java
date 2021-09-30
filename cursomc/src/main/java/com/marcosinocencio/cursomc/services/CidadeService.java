/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marcosinocencio.cursomc.services;

import com.marcosinocencio.cursomc.domain.Cidade;
import com.marcosinocencio.cursomc.repositories.CidadeRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Vinicius
 */
@Service
public class CidadeService {
    
    @Autowired
    private CidadeRepository repo;
    
    public List<Cidade> findByEstado(Integer estadoId){
       return repo.findCidades(estadoId);
    }
}
