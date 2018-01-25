package com.baeldung.beaninjectiontypes;

import org.springframework.beans.factory.annotation.Autowired;

public class LibraryConstructorInjection {

    private Book book;

    @Autowired
    public LibraryConstructorInjection(Book book) {
        this.book = book;
    }

    public Book getBook() {
        return book;
    }
}
