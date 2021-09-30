/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marcosinocencio.cursomc.repositories;

import com.marcosinocencio.cursomc.domain.Estado;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Vinicius
 */
@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer>{
    @Transactional(readOnly = true)
    public List<Estado> findAllByOrderByNome();
}
