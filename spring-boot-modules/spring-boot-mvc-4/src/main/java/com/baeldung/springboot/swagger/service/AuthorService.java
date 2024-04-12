package com.baeldung.springboot.swagger.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baeldung.springboot.swagger.model.Author;

@Service
public class AuthorService {
    private List<Author> authors = new ArrayList<>();

    public List<Author> getAllAuthors(){
        return authors;
    }

    public void addAuthors(Author author){
        author.setId(authors.size()+1);
        authors.add(author);
    }

}
