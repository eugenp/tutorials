package com.baeldung.monads;

import java.util.List;

public class AuthorsService {

    private final List<Author> authors;

    public AuthorsService(List<Author> authors) {
        this.authors = authors;
    }

    public Author findById(Long authorId) {
        return authors.stream()
            .filter(author -> author.getAuthorId().equals(authorId))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Author not found"));
    }
}
