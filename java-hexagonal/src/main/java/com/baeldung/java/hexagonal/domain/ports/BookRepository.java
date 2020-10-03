package com.baeldung.java.hexagonal.domain.ports;

import com.baeldung.java.hexagonal.domain.model.Book;

import java.util.Optional;

public interface BookRepository {

     Book saveBook(Book book);

     Optional<Book> findByName(String name);
}
