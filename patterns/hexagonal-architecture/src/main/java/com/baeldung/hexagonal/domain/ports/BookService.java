package com.baeldung.hexagonal.domain.ports;

import com.baeldung.hexagonal.domain.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    int addBook(Book book);

    Optional<Book> getBook(int id);

    List<Book> getAllBooks();
}
