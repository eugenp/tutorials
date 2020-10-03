package com.baeldung.java.hexagonal.service;

import com.baeldung.java.hexagonal.model.Book;
import com.baeldung.java.hexagonal.ports.inbound.BookRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class InMemoryBookRepository implements BookRepository {

    private List<Book> books = new ArrayList<>();

    @Override
    public Book saveBook(Book book) {
        Book savedBook = new Book()
                .setName(book.getName())
                .setId(String.valueOf(book.hashCode()));
        books.add(savedBook);
        return savedBook;
    }

    @Override
    public Optional<Book> findByName(String name) {
        return books.stream()
                .filter(book -> Objects.equals(book.getName(), name))
                .findAny();
    }
}
