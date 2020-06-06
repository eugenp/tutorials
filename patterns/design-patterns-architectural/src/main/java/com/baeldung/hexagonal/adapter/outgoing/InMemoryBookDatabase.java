package com.baeldung.hexagonal.adapter.outgoing;

import com.baeldung.hexagonal.domain.Book;
import com.baeldung.hexagonal.port.outgoing.BookRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryBookDatabase implements BookRepository {
    private List<Book> database = new ArrayList<>();

    public InMemoryBookDatabase() {
        database.add(new Book("Effective Java 3rd Edition", "Joshua Bloch"));
        database.add(new Book("Clean Code", "Robert C. Martin"));
        database.add(new Book("Refactoring 2nd Edition", "Martin Fowler"));
    }

    @Override
    public List<Book> getAllBooks() {
        return database;
    }
}
