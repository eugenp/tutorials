package com.baeldung.spring.oracle.pooling.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.spring.oracle.pooling.entity.Book;
import com.baeldung.spring.oracle.pooling.exception.BookstoreException;
import com.baeldung.spring.oracle.pooling.repository.BookRepository;

@RestController
@RequestMapping("/books")
public class BookstoreController {

    @Autowired
    private BookRepository repository;

    @GetMapping(value = "/hello")
    public String sayHello() {
        return "Hello";
    }

    @GetMapping("/all")
    public List<Book> findAll() {
        return repository.findAll();
    }

    @PostMapping("/create")
    public Book newBook(@RequestBody Book newBook) {
        return repository.save(newBook);
    }

    @GetMapping("/get/{id}")
    public Book findOne(@PathVariable Long id) throws BookstoreException {
        return repository.findById(id)
            .orElseThrow(BookstoreException::new);
    }

}
