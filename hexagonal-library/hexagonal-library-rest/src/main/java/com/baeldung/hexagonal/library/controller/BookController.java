package com.baeldung.hexagonal.library.controller;

import com.baeldung.hexagonal.library.domain.Book;
import com.baeldung.hexagonal.library.domain.BookSearchCriteria;
import com.baeldung.hexagonal.library.service.BookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public List<Book> findAll(@RequestParam String title) {
        return bookService.findAll(new BookSearchCriteria(title));
    }
}
