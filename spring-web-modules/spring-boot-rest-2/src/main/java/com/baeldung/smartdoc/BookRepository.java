package com.baeldung.smartdoc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

@Repository
public class BookRepository {

    private static final Map<Long, Book> books = new ConcurrentHashMap<>();

    static {
        Book book = new Book(1L, "George Martin", "A Song of Ice and Fire", 10000.00);
        books.put(1L, book);
    }

    public Optional<Book> findById(long id) {
        return Optional.ofNullable(books.get(id));
    }

    public void add(Book book) {
        books.put(book.getId(), book);
    }

    public List<Book> getBooks() {
        return new ArrayList<>(books.values());
    }

    public boolean delete(Book book) {
        return books.remove(book.getId(), book);
    }

}
