/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marcosinocencio.workshopmongo.repository;

import com.marcosinocencio.workshopmongo.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Vinicius
 */
@Repository
public interface UserRepository extends MongoRepository<User, String>{
    
}
