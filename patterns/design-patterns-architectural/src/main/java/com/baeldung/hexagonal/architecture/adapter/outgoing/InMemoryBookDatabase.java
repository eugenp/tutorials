package com.baeldung.hexagonal.architecture.adapter.outgoing;

import com.baeldung.hexagonal.architecture.domain.Book;
import com.baeldung.hexagonal.architecture.port.outgoing.BookRepository;

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
        database.put(UUID.randomUUID(), new Book("978-0132350884", "Clean Code: A Handbook of Agile Software Craftsmanship", "Robert C. Martin"));
        database.put(UUID.randomUUID(), new Book("978-0134757599", "Refactoring: Improving the Design of Existing Code 2nd Edition", "Martin Fowler"));
        database.put(UUID.randomUUID(), new Book("978-0321200686", "Enterprise Integration Patterns: Designing, Building, and Deploying Messaging Solutions", "Gregor Hohpe, Bobby Woolf"));
    }
}
