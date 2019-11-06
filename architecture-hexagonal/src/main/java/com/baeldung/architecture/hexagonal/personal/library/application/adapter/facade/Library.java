package com.baeldung.architecture.hexagonal.personal.library.application.adapter.facade;

import com.baeldung.architecture.hexagonal.personal.library.core.domain.Book;

import java.util.List;

public interface Library {

    void addBook(Book book);

    List<Book> getAllBooks();

    void removeBook(String isbn);

    void removeAllBooks();
}
