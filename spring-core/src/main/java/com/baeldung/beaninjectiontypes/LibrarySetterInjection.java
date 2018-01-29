package com.baeldung.beaninjectiontypes;

import org.springframework.beans.factory.annotation.Autowired;

public class LibrarySetterInjection {
    private Book book;

    @Autowired
    public void setBook(Book book) {
        this.book = book;
    }

    public Book getBook() {
        return book;
    }
}
