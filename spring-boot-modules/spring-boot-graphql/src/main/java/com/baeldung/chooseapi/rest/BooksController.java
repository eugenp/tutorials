package com.baeldung.chooseapi.rest;

import com.baeldung.chooseapi.Book;
import com.baeldung.chooseapi.BooksService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BooksController {

    private final BooksService booksService;

    public BooksController(BooksService booksService) {
        this.booksService = booksService;
    }

    @GetMapping("/rest/books")
    public List<Book> all() {
        return booksService.getBooks();
    }

}
