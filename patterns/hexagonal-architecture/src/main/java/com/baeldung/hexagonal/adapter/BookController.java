package com.baeldung.hexagonal.adapter;

import com.baeldung.hexagonal.domain.Book;
import com.baeldung.hexagonal.port.BookInboundPort;
import com.baeldung.hexagonal.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
public class BookController implements BookInboundPort {

    @Autowired
    private BookService bookService;

    @PostMapping(value = "/book")
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody Book book) {
        try {
            bookService.addBook(book.getName(), book.getAuthor(), book.getPrice());
        } catch (RuntimeException exc) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, exc.getMessage());
        }
    }

    @Override
    @GetMapping(value = "/book/{name}")
    public Book search(@PathVariable String name) {
        Book book = bookService.search(name);
        if (null == book) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "book '" + name + "' is not found");
        }
        return book;
    }
}
