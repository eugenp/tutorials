package com.baeldung.hexagonal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.hexagonal.model.Book;
import com.baeldung.hexagonal.port.BookOutboundPort;

@Service
public class BookService {
    @Autowired
    private BookOutboundPort bookOutboundPort;

    public void create(Book book) {
        this.bookOutboundPort.saveBook(book);
    }

    public Book get(String title) {
        return this.bookOutboundPort.getBook(title);
    }
}
