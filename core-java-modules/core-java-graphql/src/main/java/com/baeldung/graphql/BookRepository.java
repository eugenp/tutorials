package com.baeldung.graphql;

import java.util.ArrayList;
import java.util.List;

public class BookRepository {

    private final List<Book> books;

    public BookRepository() {
        books = new ArrayList<>();
        books.add(new Book("Title 1", "Author 1"));
        books.add(new Book("Title 2", "Author 2"));
    }

    public List<Book> getAllBooks() {
        return books;
    }

}
