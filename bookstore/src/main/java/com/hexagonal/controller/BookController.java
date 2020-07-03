package com.hexagonal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hexagonal.domain.Book;
import com.hexagonal.service.BookService;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @RequestMapping("/book/add")
    @PostMapping(produces = { MediaType.TEXT_PLAIN_VALUE })
    public void addBook(@RequestBody Book book) {
        bookService.addBook(book);
    }

    public Book buyBook(@PathVariable String isbn) {
        return bookService.buyBook(isbn);
    }

    public List<Book> listBooks() {
        return bookService.listBooks();
    }

}
