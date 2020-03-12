package com.baeldung.hexagonal.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonal.model.Book;
import com.baeldung.hexagonal.port.BookInboundPort;
import com.baeldung.hexagonal.service.BookService;

@RestController("/book")
public class RestfulBookAdapter implements BookInboundPort {
    @Autowired
    private BookService bookService;

    @Override
    @PutMapping("/create")
    public void createBook(@RequestBody Book book) {
        this.bookService.create(book);
    }

    @Override
    @GetMapping("/get/{title}")
    public Book getBook(@PathVariable String title) {
        return this.bookService.get(title);
    }

}
