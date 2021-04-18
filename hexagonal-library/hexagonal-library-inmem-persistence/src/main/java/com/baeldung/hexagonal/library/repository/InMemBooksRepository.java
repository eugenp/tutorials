package com.baeldung.hexagonal.library.repository;

import com.baeldung.hexagonal.library.domain.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class InMemBooksRepository implements BookRepository {

    private final Map<UUID, Book> bookMap;

    public InMemBooksRepository(Map<UUID, Book> bookMap) {
        this.bookMap = bookMap;
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(bookMap.values());
    }
}
