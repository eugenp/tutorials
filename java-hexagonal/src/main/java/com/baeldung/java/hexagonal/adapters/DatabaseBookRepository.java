package com.baeldung.java.hexagonal.adapters;

import com.baeldung.java.hexagonal.domain.ports.BookRepository;
import com.baeldung.java.hexagonal.domain.model.Book;
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
