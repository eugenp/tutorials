package com.baeldung.hexagoninjava.core.domain;


import java.io.Serializable;

public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private String[] authors;

    public Book(String name) {
        super();
        this.name = name;
    }

    public Book(String name, String[] authors) {
        super();
        this.name = name;
        this.authors = authors;
    }

    public String getName() {
        return name;
    }

    public String[] getAuthors() {
        return authors;
    }

    public void setAuthors(String[] authors) {
        this.authors = authors;
    }
}