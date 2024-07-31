package com.baeldung.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.baeldung.web.dto.Book;

@Repository
public class BookRepository {

    private List<Book> books = new ArrayList<>();

    public Optional<Book> findById(long id) {
        return books.stream()
            .filter(book -> book.getId() == id)
            .findFirst();
    }

    public void add(Book book) {
        books.add(book);
    }
}
