package com.baeldung.hexagonal.controllers;

import com.baeldung.hexagonal.apis.BookAuthorApi;
import com.baeldung.hexagonal.core.BookAuthorService;
import com.baeldung.hexagonal.domain.AuthorDto;
import com.baeldung.hexagonal.domain.BookDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController implements BookAuthorApi {

    private final BookAuthorService bookAuthorService;

    public ApiController(BookAuthorService bookAuthorService) {
        this.bookAuthorService = bookAuthorService;
    }

    @Override
    public ResponseEntity<?> getBooks() {
        return new ResponseEntity<>(bookAuthorService.getAllBook(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> addBook(BookDto bookDto) {
        bookAuthorService.addBook(bookDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> getAuthors() {
		return new ResponseEntity<>(bookAuthorService.getAllAuthor(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> addAuthor(AuthorDto authorDto) {
		bookAuthorService.addAuthor(authorDto);
		return new ResponseEntity<>(HttpStatus.CREATED);
    }
}