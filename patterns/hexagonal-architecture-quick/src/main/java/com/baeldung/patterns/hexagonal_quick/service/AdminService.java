package com.baeldung.patterns.hexagonal_quick.service;

import org.springframework.stereotype.Service;

import com.baeldung.patterns.hexagonal_quick.domain.Book;
import com.baeldung.patterns.hexagonal_quick.exception.BookNotFoundException;
import com.baeldung.patterns.hexagonal_quick.port.AdminInputPort;
import com.baeldung.patterns.hexagonal_quick.port.BookOutputPort;

@Service
public class AdminService implements AdminInputPort {
    private final BookOutputPort bookOutputPort;

    public AdminService(BookOutputPort bookOutputPort) {
        this.bookOutputPort = bookOutputPort;
    }

    @Override
    public Book getBook(String isbn) {
        return bookOutputPort.findBookByIsbn(isbn)
            .orElseThrow(() -> new BookNotFoundException(isbn));
    }

    @Override
    public Book addBook(Book book) {
        return bookOutputPort.findBookByIsbn(book.getIsbn())
            .orElseGet(() -> bookOutputPort.createBook(book));
    }
}