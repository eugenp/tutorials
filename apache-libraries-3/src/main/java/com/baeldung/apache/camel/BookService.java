package com.baeldung.apache.camel;

import java.util.ArrayList;
import java.util.List;

public class BookService {

    private final List<Book> books = new ArrayList<>();

    BookService() {
        books.add(new Book("1", "Clean Code", "Baeldung"));
        books.add(new Book("2", "Effective Java", "Joshua"));
    }

    List<Book> getBooks() {
        return books;
    }

    Book getBookById(String id) {
        return books.stream()
            .filter(b -> b.getId()
                .equals(id))
            .findFirst()
            .orElse(null);
    }

    Book addBook(Book book) {
        books.add(book);
        return book;
    }
}
