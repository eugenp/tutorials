package com.baeldung.java.hexagonal.service;

import com.baeldung.java.hexagonal.model.Book;
import com.baeldung.java.hexagonal.ports.inbound.BookRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DatabaseBookRepository implements BookRepository {

    @Override
    public Book saveBook(Book book) {
        // persist me in a database
        return null;
    }

    @Override
    public Optional<Book> findByName(String name) {
        return Optional.empty();
    }
}
