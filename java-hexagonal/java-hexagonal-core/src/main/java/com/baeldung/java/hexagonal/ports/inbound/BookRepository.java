package com.baeldung.java.hexagonal.ports.inbound;

import com.baeldung.java.hexagonal.model.Book;

import java.util.Optional;

public interface BookRepository {

     Book saveBook(Book book);

     Optional<Book> findByName(String name);
}
