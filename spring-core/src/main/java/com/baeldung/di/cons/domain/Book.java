package com.baeldung.di.cons.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Book {

    private Author author;

    @Autowired
    public Book(Author author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book [author=" + author + "]";
    }

}
