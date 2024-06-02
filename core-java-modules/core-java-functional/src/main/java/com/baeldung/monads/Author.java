package com.baeldung.monads;

import java.util.List;

public class Author {

    private String name;
    private Long authorId;
    private List<String> genres;
    private List<Book> books;

    public Author(String name, Long id, List<String> genres, List<Book> books) {
        this.name = name;
        this.authorId = id;
        this.genres = genres;
        this.books = books;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public String getName() {
        return name;
    }

    public List<String> getGenres() {
        return genres;
    }

    public List<Book> getBooks() {
        return books;
    }
}
