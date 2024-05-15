package com.baeldung.microstream;

import one.microstream.reference.Lazy;

import java.util.ArrayList;
import java.util.List;

public class RootInstanceLazy {

    private final String name;
    private final Lazy<List<Book>> books;

    public RootInstanceLazy(String name) {
        this.name = name;
        books = Lazy.Reference(new ArrayList<>());
    }

    public String getName() {
        return name;
    }

    public List<Book> getBooks() {
        return Lazy.get(books);
    }

}
