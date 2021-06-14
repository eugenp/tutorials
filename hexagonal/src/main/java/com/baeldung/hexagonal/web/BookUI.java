package com.baeldung.hexagonal.web;

import com.baeldung.hexagonal.core.domain.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface BookUI {

    @PostMapping
    void addBook(@RequestBody Book book);

    @GetMapping("/{title}")
    public Book getBook(@PathVariable String title);
}
