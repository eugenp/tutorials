package com.baeldung.architecture.hexagonal.personal.library.application.adapter.infrastructure;

import com.baeldung.architecture.hexagonal.personal.library.core.domain.Book;
import com.baeldung.architecture.hexagonal.personal.library.core.port.infrastructure.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryBookRepository implements BookRepository {

    private final ConcurrentHashMap<String, Book> map = new ConcurrentHashMap();

    @Override
    public Book create(Book book) {
        return map.put(book.getIsbn(), book);
    }

    @Override
    public boolean remove(String isbn) {
        return map.remove(isbn) != null;
    }

    @Override
    public List<Book> getAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Book getBookByIsbn(String isbn) {
        return map.get(isbn);
    }

    @Override
    public void removeAll() {
        map.clear();
    }
}
