package com.baeldung.microstream;

import java.util.ArrayList;
import java.util.List;

public class RootInstance {

    private final String name;
    private final List<Book> books;

    public RootInstance(String name) {
        this.name = name;
        books = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Book> getBooks() {
        return books;
    }
}
