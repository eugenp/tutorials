package com.baeldung.apache.camel;

import java.util.ArrayList;
import java.util.List;

public class BookService {

    private final List<Book> books = new ArrayList<>();

    public BookService() {
        books.add(new Book("1", "Clean Code", "Robert"));
        books.add(new Book("2", "Effective Java", "Joshua"));
    }

    public List<Book> getBooks() {
        return books;
    }

    public Book getBookById(String id) {
        return books.stream()
            .filter(b -> b.getId()
                .equals(id))
            .findFirst()
            .orElse(null);
    }

    public Book addBook(Book book) {
        books.add(book);
        return book;
    }
}
