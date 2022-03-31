package com.baeldung.copyobjects.eval;

public class Author  {
    private String name;

    public Author(Author author) {
        this(author.getName());
    }

    public Author(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
