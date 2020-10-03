package com.baeldung.java.hexagonal.service;


import com.baeldung.java.hexagonal.model.BookCreateRequest;
import com.baeldung.java.hexagonal.model.BookCreateResponse;
import com.baeldung.java.hexagonal.ports.inbound.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookRequestService {

    @Autowired
    BookService bookService;

    public BookCreateResponse createBook(BookCreateRequest bookCreateRequest) {
        String name = bookCreateRequest.getName();
        String id = bookService.createBook(name);
        return new BookCreateResponse().
                setId(id)
                .setName(name);
    }
}
