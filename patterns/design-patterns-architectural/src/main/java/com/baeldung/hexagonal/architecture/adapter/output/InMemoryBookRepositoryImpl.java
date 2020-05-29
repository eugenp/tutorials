package com.baeldung.hexagonal.architecture.adapter.output;

import com.baeldung.hexagonal.architecture.domain.Book;
import com.baeldung.hexagonal.architecture.output.BookRepository;

import java.util.*;

// adapter
public class InMemoryBookRepositoryImpl implements BookRepository {

    private Map<UUID, Book> database = new HashMap<>();

    @Override
    public Book getBookById(UUID id) {
        return database.get(id);
    }

    @Override
    public void saveBook(Book book) {
        database.putIfAbsent(book.getId(), book);
    }

    @Override
    public List<Book> getAllBooks() {
        return new ArrayList<>(database.values());
    }
}
