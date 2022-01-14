package com.baeldung.pattern.hexagonal2.controller;

import com.baeldung.pattern.hexagonal2.domain.model.Book;
import com.baeldung.pattern.hexagonal2.domain.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping(path = "/{isbn}")
    public Book getBook(@PathVariable("isbn") String isbn) {
        return bookService.getBook(isbn);
    }
}
