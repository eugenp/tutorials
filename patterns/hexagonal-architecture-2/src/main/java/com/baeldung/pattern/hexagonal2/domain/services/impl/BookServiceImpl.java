package com.baeldung.pattern.hexagonal2.domain.services.impl;

import com.baeldung.pattern.hexagonal2.domain.model.Book;
import com.baeldung.pattern.hexagonal2.domain.port.BookServicePort;
import com.baeldung.pattern.hexagonal2.domain.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookServicePort port;

    public Book getBook(String isbn) {
        if (isbn.length() < 10) {
            throw new IllegalArgumentException("ISBN is not valid");
        }
        Book book = new Book();
        book.setTitle(port.getBookTitle(isbn));
        book.setIsbn(isbn);
        return book;
    }

}
