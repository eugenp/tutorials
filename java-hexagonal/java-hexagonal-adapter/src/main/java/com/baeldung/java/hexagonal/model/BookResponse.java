package com.baeldung.java.hexagonal.model;

public class BookResponse {

    private String id;

    private String name;

    public String getId() {
        return id;
    }

    public BookResponse setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public BookResponse setName(String name) {
        this.name = name;
        return this;
    }
}
