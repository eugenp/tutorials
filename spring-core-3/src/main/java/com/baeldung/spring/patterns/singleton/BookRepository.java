package com.baeldung.spring.patterns.singleton;

import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public class BookRepository {

    public long count() {
        return 1;
    }
    
    public Optional<Book> findById(long id) {
        return Optional.of(new Book());
    }
}
