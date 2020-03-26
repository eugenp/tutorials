package com.baeldung.library.inbound.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.library.domain.Book;
import com.baeldung.library.inbound.port.IBookService;

@RestController
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    IBookService bookService;

    @PostMapping
    public void publishBook(@RequestBody Book book) {
        bookService.publishBook(book);
    }
}