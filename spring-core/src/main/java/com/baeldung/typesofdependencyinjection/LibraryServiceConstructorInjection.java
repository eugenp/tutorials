package com.baeldung.typesofdependencyinjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LibraryServiceConstructorInjection {

    private Book book;

    @Autowired
    public LibraryServiceConstructorInjection(Book book){
        this.book = book;
    }

    public String getNewBook(String name){
        return book.getNewBook(name);
    }

}
