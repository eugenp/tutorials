package com.baeldung.hexagonal.domain.ports;

import com.baeldung.hexagonal.domain.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    int storeBook(Book book);

    Optional<Book> findById(int id);

    List<Book> findAll();
}
