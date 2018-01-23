package com.baeldung.beaninjectiontypes;

public class LibraryConstructorInjection {

    private String book;

    public LibraryConstructorInjection(String book) {
        this.book = book;
    }
    
    public String getBook() {
        return book;
    }
}
