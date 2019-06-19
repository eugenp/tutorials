package com.baeldung.hexagonalarchitecturejava;

import java.util.List;

public interface BookService {

    void addBook(Book book);

    void removeBook(Long bookId);

    List<Book> getAllBooks();
}
