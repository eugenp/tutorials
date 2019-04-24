package com.baeldung.hexagonal.adapters;

import com.baeldung.hexagonal.domain.models.Book;
import com.baeldung.hexagonal.domain.ports.BookRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryBookRepositoryAdapter implements BookRepository {

    private Map<Integer, Book> bookStore = new HashMap<>();
    private AtomicInteger bookIdGenerator = new AtomicInteger(0);

    @Override
    public int storeBook(Book book) {
        book.setId(bookIdGenerator.incrementAndGet());
        bookStore.put(book.getId(), book);
        return book.getId();
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(bookStore.values());
    }

    @Override
    public Optional<Book> findById(int id) {
        return bookStore.values()
                .stream()
                .filter(book -> book.getId() == id)
                .findFirst();
    }
}
