package com.baeldung.springdatageode.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.springdatageode.domain.Author;
import com.baeldung.springdatageode.repo.AuthorRepository;

@RestController
@CrossOrigin
public class AppController {

    @Autowired
    private AuthorRepository authorRepositoryImpl;

    @PostMapping(path = "/author" )
    public ResponseEntity<String> addAuthor(@RequestBody Author author) throws Exception {       
        authorRepositoryImpl.save(author);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
    
    @GetMapping(path = "/author" )
    public ResponseEntity<Author> getAuthor(@RequestParam("id") String id) throws Exception {
        Optional<Author> author = authorRepositoryImpl.findById(Long.parseLong(id));
        return new ResponseEntity<>(author.isPresent() ? author.get() : null, HttpStatus.OK);
    }

}
