package com.baeldung.hexagonalarchitecture;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InMemoryBookRepository implements BookRepository {

    Map<Integer, Book> booksDataStore = new HashMap<>();

    @Override
    public List<Book> findAll() {
        return booksDataStore.values()
          .stream()
          .collect(Collectors.toList());
    }

    @Override
    public void save(Book book) {
        booksDataStore.putIfAbsent(book.getId(), book);
    }

    @Override
    public Book findById(Integer id) {
        return booksDataStore.get(id);
    }

    @Override
    public List<Book> findByGenre(BookGenre genre) {
        Collection<Book> books = booksDataStore.values();
        return books.stream()
          .filter(b -> b.getGenre().equals(genre))
          .collect(Collectors.toList());
    }
}
