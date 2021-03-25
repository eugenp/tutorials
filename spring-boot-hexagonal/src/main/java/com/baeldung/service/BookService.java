package com.baeldung.service;

import java.util.List;

import com.baeldung.domain.Book;

public interface BookService {
    void createOne(Book book);

    Book findOne(Long id);

    List<Book> findAll();
}
