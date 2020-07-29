package com.baeldung.hexagonal.controllers;

import com.baeldung.hexagonal.core.BookAuthorService;
import com.baeldung.hexagonal.domain.AuthorDto;
import com.baeldung.hexagonal.domain.BookDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    private final BookAuthorService bookAuthorService;

    public ApiController(BookAuthorService bookAuthorService) {
        this.bookAuthorService = bookAuthorService;
    }

    @GetMapping(value = "/books", produces = { "application/json" })
    public ResponseEntity<?> getBooks() {
        return new ResponseEntity<>(bookAuthorService.getAllBook(), HttpStatus.OK);
    }

    @PostMapping(value = "/books", produces = { "application/json" })
    public ResponseEntity<?> addBook(BookDto bookDto) {
        bookAuthorService.addBook(bookDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/authors", produces = { "application/json" })
    public ResponseEntity<?> getAuthors() {
        return new ResponseEntity<>(bookAuthorService.getAllAuthor(), HttpStatus.OK);
    }

    @PostMapping(value = "/authors", produces = { "application/json" })
    public ResponseEntity<?> addAuthor(AuthorDto authorDto) {
        bookAuthorService.addAuthor(authorDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}