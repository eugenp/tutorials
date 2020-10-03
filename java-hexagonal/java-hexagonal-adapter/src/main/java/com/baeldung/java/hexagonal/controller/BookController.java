package com.baeldung.java.hexagonal.controller;

import com.baeldung.java.hexagonal.model.BookCreateRequest;
import com.baeldung.java.hexagonal.model.BookCreateResponse;
import com.baeldung.java.hexagonal.service.BookRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    @Autowired
    BookRequestService bookRequestService;

    @PostMapping("/books")
    public BookCreateResponse createBook(@RequestBody BookCreateRequest bookCreateRequest){
        return bookRequestService.createBook(bookCreateRequest);
    }
}
