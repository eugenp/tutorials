package com.baeldung.java.hexagonal;

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
