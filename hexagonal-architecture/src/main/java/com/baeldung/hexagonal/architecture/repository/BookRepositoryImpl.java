package com.baeldung.hexagonal.architecture.repository;

import com.baeldung.hexagonal.architecture.core.BookRepository;
import com.baeldung.hexagonal.architecture.model.Book;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BookRepositoryImpl implements BookRepository {
    private final Map<Long, Book> books = new ConcurrentHashMap<>();

    @Override
    public Book loadBook(long id) {
        return books.get(id);
    }

    @Override
    public List<Book> loadBooks() {
        return Collections.unmodifiableList(new ArrayList<>(books.values()));
    }

    @Override
    public void saveBook(Book book) {
        books.put(book.getId(), book);
    }
}
