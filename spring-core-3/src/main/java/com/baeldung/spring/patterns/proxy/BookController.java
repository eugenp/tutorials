package com.baeldung.spring.patterns.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {
    
    @Autowired
    private BookManager manager;

    @PostMapping("/book")
    public Book create(@RequestParam String author) {
        return manager.create(author);
    }
}
