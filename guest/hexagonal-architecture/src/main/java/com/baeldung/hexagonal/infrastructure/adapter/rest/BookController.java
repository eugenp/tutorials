package com.baeldung.hexagonal.infrastructure.adapter.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.baeldung.hexagonal.domain.model.Book;

public interface BookController {

    @GetMapping("/book")
    ResponseEntity<List<Book>> getBooks();

    @PostMapping("/book")
    ResponseEntity<Void> addBook(@RequestBody Book book);

    @DeleteMapping("/book")
    ResponseEntity<Void> removeBook(@RequestBody Book book);

    @GetMapping("/book/{bookId}")
    ResponseEntity<Book> getBookById(@PathVariable Integer bookId) throws Exception;
}