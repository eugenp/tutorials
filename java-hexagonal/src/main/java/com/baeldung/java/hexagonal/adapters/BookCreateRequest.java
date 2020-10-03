package com.baeldung.java.hexagonal.adapters;

public class BookCreateRequest {

    private String name;

    public String getName() {
        return name;
    }

    public BookCreateRequest setName(String name) {
        this.name = name;
        return this;
    }
}
