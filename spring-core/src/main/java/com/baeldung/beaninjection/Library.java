package com.baeldung.beaninjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Library {

    private Librarian librarian;

    @Autowired
    public void setLibrarian(Librarian librarian) {
        this.librarian = librarian;
    }

    public Librarian getLibrarian() {
        return librarian;
    }
}

