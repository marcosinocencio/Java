/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marcosinocencio.workshopmongo.services;

import com.marcosinocencio.workshopmongo.domain.Post;
import com.marcosinocencio.workshopmongo.repository.PostRepository;
import com.marcosinocencio.workshopmongo.services.exception.ObjectNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Vinicius
 */
@Service
public class PostService {
    
    @Autowired
    private PostRepository repo; 
    
    public Post findById(String id){
        Optional<Post> obj = repo.findById(id); 
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }
    
    public List<Post> findByTitle(String text){
        return repo.searchTitle(text);
    }
    
    public List<Post> fullSearch(String text, Date minDate, Date maxDate){
        maxDate = new Date(maxDate.getTime() + 24 * 60 * 60 * 1000);
        return repo.fullSearch(text, minDate, maxDate);
    }
    
}
