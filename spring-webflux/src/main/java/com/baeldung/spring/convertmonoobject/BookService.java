package com.baeldung.spring.convertmonoobject;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class BookService {

    public Mono<Book> getBook(String bookId) {
        return Mono.empty();
    }
}
