package com.baeldung.hexagonal.architecture.port.outgoing;

import com.baeldung.hexagonal.architecture.domain.Book;

import java.util.List;

public interface BookRepository {
    List<Book> getAllBooks();
}