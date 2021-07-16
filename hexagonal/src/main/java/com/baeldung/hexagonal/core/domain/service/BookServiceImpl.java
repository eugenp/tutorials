package com.baeldung.hexagonal.core.domain.service;

import com.baeldung.hexagonal.core.domain.Book;
import com.baeldung.hexagonal.port.BookRepo;
import com.baeldung.hexagonal.port.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepo bookRepo;

    @Override
    public void addBook(Book book) {
        bookRepo.addBook(book);
    }

    @Override
    public Book getBook(String title) {
        return bookRepo.getBook(title);
    }
}
