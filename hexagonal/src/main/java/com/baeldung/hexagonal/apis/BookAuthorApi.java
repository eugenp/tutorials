package com.baeldung.hexagonal.apis;

import com.baeldung.hexagonal.domain.AuthorDto;
import com.baeldung.hexagonal.domain.BookDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


public interface BookAuthorApi {

    @GetMapping(value = "/books", produces = {"application/json"})
    default ResponseEntity<?> getBooks() {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping(value = "/books", produces = {"application/json"})
    default ResponseEntity<?> addBook(@RequestBody BookDto bookDto) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    @GetMapping(value = "/authors", produces = {"application/json"})
    default ResponseEntity<?> getAuthors() {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping(value = "/authors", produces = {"application/json"})
    default ResponseEntity<?> addAuthor(@RequestBody AuthorDto authorDto) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }
}
