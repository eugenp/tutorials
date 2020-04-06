package com.baeldung.hexagonal.bookstore.rest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.baeldung.hexagonal.bookstore.core.Book;

public interface BookRest {

    @PostMapping
    void createBook(@RequestBody Book book);
    
    @GetMapping("/{title}")
    Book getBook(@PathVariable String title);
    
    @GetMapping
    List<Book> getAllBooks();
}
