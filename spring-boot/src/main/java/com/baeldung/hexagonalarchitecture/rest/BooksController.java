package com.baeldung.hexagonalarchitecture.rest;


import com.baeldung.hexagonalarchitecture.contract.BooksService;
import com.baeldung.hexagonalarchitecture.model.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BooksController {

    private BooksService booksService;

    public BooksController(BooksService booksService) {
        this.booksService = booksService;
    }

    @GetMapping("/myBooks")
    public List<Book> getBooks() {
        return booksService.retrieveBooks();
    }
}
