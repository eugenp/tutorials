package com.baeldung.java.hexagonal.controller;

import com.baeldung.java.hexagonal.model.BookCreateRequest;
import com.baeldung.java.hexagonal.ports.inbound.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("/books")
    public String createBook(@RequestBody BookCreateRequest bookCreateRequest) {
        return bookService.createBook(bookCreateRequest.getName());
    }
}
