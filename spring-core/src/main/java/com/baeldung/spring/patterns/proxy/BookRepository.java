package com.baeldung.spring.patterns.proxy;

import org.springframework.stereotype.Repository;

@Repository
public class BookRepository {

    public Book create(String author) {
        return new Book(author);
    }
}
