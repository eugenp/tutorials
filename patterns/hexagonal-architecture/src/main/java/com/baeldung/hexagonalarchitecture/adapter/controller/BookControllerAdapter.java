package com.baeldung.hexagonalarchitecture.adapter.controller;

import com.baeldung.hexagonalarchitecture.core.domain.Book;
import com.baeldung.hexagonalarchitecture.port.BookServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookControllerAdapter {

    @Autowired
    private BookServicePort bookService;

    @GetMapping("/books")
    public Book getBook(@RequestParam Long id) {
        return bookService.getBook(id);
    }
}
