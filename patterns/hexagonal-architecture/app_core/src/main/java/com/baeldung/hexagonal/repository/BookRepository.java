package com.baeldung.hexagonal.repository;

import java.util.List;
import java.util.Optional;

import com.baeldung.hexagonal.domain.Book;

public interface BookRepository {

    public Book save(Book book);

    public Optional<Book> findBookByIsbn(String isbn);

    public void delete(Book book);

    public List<Book> findAll();

}
