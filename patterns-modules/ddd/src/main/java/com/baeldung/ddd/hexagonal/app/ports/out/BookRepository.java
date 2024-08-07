package com.baeldung.ddd.hexagonal.app.ports.out;

import java.util.List;

import com.baeldung.ddd.hexagonal.app.domain.Book;

public interface BookRepository {
	Book save(Book book);
    Book findById(Long bookId);
    List<Book> findAllBooks();
}
