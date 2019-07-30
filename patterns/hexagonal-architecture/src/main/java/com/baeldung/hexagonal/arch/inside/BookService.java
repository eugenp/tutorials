package com.baeldung.hexagonal.arch.inside;

import java.util.List;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookService {

    private final Bookstore bookstore;

    public void save(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Book title must be specified");
        }
        bookstore.create(title);
    }

    public List<Book> getBooks() {
        return bookstore.getBooks();
    }
}
