package com.baeldung.hexagonalPattern.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.baeldung.hexagonalPattern.core.domain.Book;

public interface LibraryRestUI {
    // This is the in bound Adapter

    @PostMapping
    public int insertBook(@RequestBody Book book);

    @GetMapping
    public Book searchBook(@PathVariable String name);

    @GetMapping
    public List<Book> listAllBooks();

}
