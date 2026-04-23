package com.example.demo.controller;

import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookRepository repo;

    public BookController(BookRepository repo) {
        this.repo = repo;
    }


    @PostMapping
    public Book create(@RequestBody Book book) {
        return repo.save(book);
    }


    @GetMapping
    public List<Book> findAll() {
        return repo.findAll();
    }
}

