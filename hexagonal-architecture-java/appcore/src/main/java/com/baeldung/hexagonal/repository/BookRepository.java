package com.baeldung.hexagonal.repository;

import com.baeldung.hexagonal.domain.Book;

import java.util.List;
import java.util.Optional;


public interface BookRepository {

    int addBook(Book newBook);

    List<Book> getAllBooks();

    Optional<List<Book>> findBookByName(String name);

}