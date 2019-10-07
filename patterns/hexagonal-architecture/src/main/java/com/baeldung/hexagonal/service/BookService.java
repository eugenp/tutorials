package com.baeldung.hexagonal.service;

import static org.springframework.util.StringUtils.hasText;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.hexagonal.domain.Book;
import com.baeldung.hexagonal.port.BookOutboundPort;

@Service
public class BookService {

    @Autowired
    private BookOutboundPort bookOutboundPort;

    public void addBook(String name, String author, int price) {

        validate(name, author, price);
        Book book = new Book(name, author, price);

        bookOutboundPort.addBook(book);
    }

    public Book search(String name) {
        return bookOutboundPort.getBook(name);
    }

    private void validate(String name, String author, int price) {
        if (!hasText(name)) {
            throw new RuntimeException("Missing mandatory field 'name'");
        }
        if (!hasText(author)) {
            throw new RuntimeException("Missing mandatory field 'author'");
        }
        if (price <= 0) {
            throw new RuntimeException("Price of book must be bigger than 0");
        }
    }
}
