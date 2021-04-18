package com.baeldung.hexagonal.library.domain;

public class BookSearchCriteria {
    private final String title;

    public BookSearchCriteria(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
