package com.baeldung.typesofdependencyinjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LibraryServiceFieldInjection {

    @Autowired
    private Book book;

    public String getNewBook(String name) {
        return book.getNewBook(name);
    }
}
