package com.baeldung.hexagonal.architecture.adapter.outgoing;

import com.baeldung.hexagonal.architecture.domain.Book;
import com.baeldung.hexagonal.architecture.port.outgoing.BookRepository;

import java.util.*;

public class InMemoryBookDatabase implements BookRepository {
    private List<Book> database = new ArrayList<>();

    public InMemoryBookDatabase() {
        database.add(new Book("978-0134685991", "Effective Java 3rd Edition", "Joshua Bloch"));
        database.add(new Book("978-0132350884", "Clean Code", "Robert C. Martin"));
        database.add(new Book("978-0134757599", "Refactoring 2nd Edition", "Martin Fowler"));
    }

    @Override
    public List<Book> getAllBooks() {
        return database;
    }
}
