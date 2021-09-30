/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marcosinocencio.cursomc.repositories;

import com.marcosinocencio.cursomc.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Vinicius
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
    @Transactional(readOnly = true)
    Cliente findByEmail(String email);
}
