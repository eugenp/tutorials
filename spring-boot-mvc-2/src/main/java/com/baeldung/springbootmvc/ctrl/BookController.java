package com.baeldung.springbootmvc.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.springbootmvc.error.BookNotFoundException;
import com.baeldung.springbootmvc.model.Book;
import com.baeldung.springbootmvc.svc.BookRepository;

@RestController
@RequestMapping("/api/book")
public class BookController {
    
    @Autowired
    private BookRepository repository;

    @GetMapping("/{id}")
    public Book findById(@PathVariable long id) {
        return repository.findById(id)
            .orElseThrow(() -> new BookNotFoundException(id));
    }
}
