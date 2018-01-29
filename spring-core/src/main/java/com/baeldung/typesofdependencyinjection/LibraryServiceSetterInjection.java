package com.baeldung.typesofdependencyinjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LibraryServiceSetterInjection {

    private Book book;

    @Autowired
    public void setBook(Book book) {
        this.book = book;
    }

    public String getNewBook(String name){
        return book.getNewBook(name);
    }
}
