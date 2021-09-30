/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marcosinocencio.cursomc.repositories;

import com.marcosinocencio.cursomc.domain.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Vinicius
 */
@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer>{
    
}
