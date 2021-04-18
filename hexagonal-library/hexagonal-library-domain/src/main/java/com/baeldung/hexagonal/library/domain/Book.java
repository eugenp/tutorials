package com.baeldung.hexagonal.library.domain;

import java.util.List;
import java.util.UUID;

public class Book {
    private UUID id;
    private String title;
    private List<String> authors;

    public Book() {
    }

    public Book(UUID id, String title, List<String> authors) {
        this.id = id;
        this.title = title;
        this.authors = authors;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authors=" + authors +
                '}';
    }
}
