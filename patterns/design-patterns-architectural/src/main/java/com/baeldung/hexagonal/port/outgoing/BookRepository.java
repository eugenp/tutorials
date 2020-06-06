package com.baeldung.hexagonal.port.outgoing;

import com.baeldung.hexagonal.domain.Book;

import java.util.List;

public interface BookRepository {
    List<Book> getAllBooks();
}
