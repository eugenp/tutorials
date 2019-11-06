package com.baeldung.architecture.hexagonal.personal.library.application.adapter.infrastructure;

import com.baeldung.architecture.hexagonal.personal.library.core.domain.Book;
import com.baeldung.architecture.hexagonal.personal.library.core.port.infrastructure.BookRepository;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import java.util.ArrayList;
import java.util.List;

public class HazelcastBookRepository implements BookRepository {

    private final HazelcastInstance hz = Hazelcast.newHazelcastInstance();

    @Override
    public Book create(Book book) {
        return (Book) hz.getMap("books").put(book.getIsbn(), book);
    }

    @Override
    public boolean remove(String isbn) {
        hz.getMap("books").delete(isbn);
        return true;
    }

    @Override
    public List<Book> getAll() {
        return new ArrayList(hz.getMap("books").values());
    }

    @Override
    public Book getBookByIsbn(String isbn) {
        return (Book) hz.getMap("books").get(isbn);
    }

    @Override
    public void removeAll() {
        hz.getMap("books").destroy();
    }
}
