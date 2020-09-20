package com.baeldung.application.service;


import com.baeldung.adapters.mock.BookMockRepository;
import com.baeldung.ports.BookRepository;
import domain.Book;

import java.util.List;

public class BookService {

    private final BookRepository bookRepository = new BookMockRepository();

    public BookService() {
    }

    public List<Book> findAllBooksFromAuthor(String author) {
        return bookRepository.findAllBooksFromAuthor(author);
    }

    public Book getBookById(String id) {
        return bookRepository.getBookById(id);
    }

}
