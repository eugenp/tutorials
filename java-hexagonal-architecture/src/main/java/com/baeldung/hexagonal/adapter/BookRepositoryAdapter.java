package com.baeldung.hexagonal.adapter;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.baeldung.hexagonal.model.Book;
import com.baeldung.hexagonal.port.BookOutboundPort;

@Repository
public class BookRepositoryAdapter implements BookOutboundPort {
    // in memory "repository"
    private final Map<String, Book> repository = new HashMap<>();

    @Override
    public void saveBook(Book book) {
        this.repository.put(book.getTitle(), book);
    }

    @Override
    public Book getBook(String title) {
        return this.repository.get(title);
    }

}
