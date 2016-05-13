package com.baeldung.spring.data.neo4j.service;

import com.baeldung.spring.data.neo4j.model.Book;

import java.util.Map;

public interface BookService {

    Book save(Book book);

    void delete(long bookId);

    long bookCount();

    Book findBookById(Long id);

    void deleteAllInGraph();
}
