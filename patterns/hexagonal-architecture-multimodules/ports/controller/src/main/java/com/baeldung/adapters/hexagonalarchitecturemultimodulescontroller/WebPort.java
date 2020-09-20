package com.baeldung.adapters.hexagonalarchitecturemultimodulescontroller;

import com.baeldung.application.service.BookService;
import domain.Book;

import java.util.List;

public class WebPort {

    private final BookService bookService = new BookService();

    public Book getBookById(String id) {
        return bookService.getBookById(id);
    }

    public List<Book> findAllBooksFromAuthor(String authorName) {
        return bookService.findAllBooksFromAuthor(authorName);
    }

}
