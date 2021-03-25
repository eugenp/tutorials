package com.baeldung.repository;

import java.util.List;

import com.baeldung.domain.Book;

public interface BookRepository {
    void createOneBook(Book book);
    Book findOneById(Long id);
    List<Book> findAllBook();
}
