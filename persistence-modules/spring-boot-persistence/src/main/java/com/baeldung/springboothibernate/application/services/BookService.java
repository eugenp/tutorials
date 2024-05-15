package com.baeldung.springboothibernate.application.services;

import com.baeldung.springboothibernate.application.models.Book;
import com.baeldung.springboothibernate.application.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> list() {
        return bookRepository.findAll();
    }
}
