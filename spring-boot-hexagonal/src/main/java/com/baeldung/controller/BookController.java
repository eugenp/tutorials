package com.baeldung.controller;

import java.util.List;

import com.baeldung.domain.Book;
import com.baeldung.service.BookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {
    
    @Autowired
    private BookService bookService;

    @PostMapping
    public void createBook(@RequestBody Book book){
        this.bookService.createOne(book);
    }

    @GetMapping("/{id}")
    public Book findOne(@PathVariable("id") Long id){
        return this.bookService.findOne(id);
    }

    @GetMapping
    public List<Book> findAll(){
        return this.bookService.findAll();
    }
}
