package com.baeldung.hexagonal.bookstore.ports;

import java.util.List;

import com.baeldung.hexagonal.bookstore.core.Book;

public interface BookRepository {

    void createBook(Book book);
    Book getBook(String title);
    List<Book> getAllBooks();
}
