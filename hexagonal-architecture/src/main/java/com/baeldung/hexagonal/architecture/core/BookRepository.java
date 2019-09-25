package com.baeldung.hexagonal.architecture.core;

import com.baeldung.hexagonal.architecture.model.Book;

import java.util.List;

public interface BookRepository {
    Book loadBook(long id);

    List<Book> loadBooks();

    void saveBook(Book book);
}
