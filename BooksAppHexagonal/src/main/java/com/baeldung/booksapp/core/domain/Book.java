package com.baeldung.booksapp.core.domain;

import java.io.Serializable;

public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    int id;
    String name;
    String author;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
