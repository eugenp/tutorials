package com.baeldung.hexagonal.architecture.adapter.out;

import com.baeldung.hexagonal.architecture.domain.Book;
import com.baeldung.hexagonal.architecture.port.out.BookRepository;

import java.util.*;

public class InMemoryBookDatabase implements BookRepository {

    private Map<UUID, Book> database = new HashMap<>();

    public InMemoryBookDatabase() {
        initialLoad();
    }

    @Override
    public List<Book> getAllBooks() {
        return new ArrayList<>(database.values());
    }

    public void initialLoad() {
        database.put(UUID.randomUUID(), new Book("1", "Title1", "Author1"));
        database.put(UUID.randomUUID(), new Book("2", "Title2", "Author2"));
        database.put(UUID.randomUUID(), new Book("3", "Title3", "Author3"));
        database.put(UUID.randomUUID(), new Book("4", "Title4", "Author4"));
    }
}
