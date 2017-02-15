package com.baeldung.ditypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ConstructorTypeBookManager {

    private BookService bookService;

    @Autowired
    public ConstructorTypeBookManager(@Qualifier("bookServiceOtherImpl") BookService bookService) {
        this.bookService = bookService;
    }

    public Boolean create(Book book) {
        return bookService.create(book);
    }

    public Book read(String isbn) {
        return bookService.read(isbn);
    }

    public Boolean update(String isbn, Book book) {
        return bookService.update(isbn, book);
    }

    public Boolean delete(String isbn) {
        return bookService.delete(isbn);
    }
}
