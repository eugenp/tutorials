package com.baeldung.hexagoninjava.core.implementation;


import java.util.List;

import com.baeldung.hexagoninjava.core.domain.Book;
import com.baeldung.hexagoninjava.port.BookRepo;
import com.baeldung.hexagoninjava.port.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepo bookRepo;

    @Override
    public void createBook(Book book) {
        bookRepo.createBook(book);
    }

    @Override
    public Book getBook(String name) {

        return bookRepo.getBook(name);
    }

    @Override
    public List<Book> listBook() {
        return bookRepo.getAllBooks();
    }

}