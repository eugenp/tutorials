package com.baeldung.hexagonal.architecture.infrastructure.driving.userside.boundary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonal.architecture.application.control.BookService;
import com.baeldung.hexagonal.architecture.application.entity.Book;

@RestController
public class BookResource {

    @Autowired
    BookService bookService;

    @PostMapping("/book")
    public void registerBook(Book book) {
        bookService.registerBook(book);
    }
}
