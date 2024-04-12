package com.baeldung.springboot.swagger.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.springboot.swagger.model.Author;
import com.baeldung.springboot.swagger.service.AuthorService;
import com.baeldung.springboot.swagger.views.Views;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping("/authors")
public class AuthorsController {

    @Autowired
    AuthorService authorService;

    @JsonView(Views.Public.class)
    @GetMapping
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @PostMapping
    public void addAuthor(@RequestBody @JsonView(Views.Public.class) Author author){
        authorService.addAuthors(author);
    }
}
