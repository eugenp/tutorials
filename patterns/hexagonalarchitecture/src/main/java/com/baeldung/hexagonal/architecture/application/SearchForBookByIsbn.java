package com.baeldung.hexagonal.architecture.application;

public class SearchForBookByIsbn {
    private String isbn;

    public SearchForBookByIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getIsbn() {
        return isbn;
    }
}
