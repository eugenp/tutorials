package com.baeldung.java.hexagonal.model;

public class BookCreateResponse {

    private String id;

    public String getId() {
        return id;
    }

    public BookCreateResponse setId(String id) {
        this.id = id;
        return this;
    }
}
