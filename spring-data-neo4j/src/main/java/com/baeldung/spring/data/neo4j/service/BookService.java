package com.baeldung.spring.data.neo4j.service;

import com.baeldung.spring.data.neo4j.model.Book;

import java.util.Map;

/**
 * Created by SDN on 5/13/2016.
 */
public interface BookService {
    Map<String, Object> graph(int limit);

    Book save(Book book);

    Book findBookById(Long id);

    void deleteAllInGraph();
}
