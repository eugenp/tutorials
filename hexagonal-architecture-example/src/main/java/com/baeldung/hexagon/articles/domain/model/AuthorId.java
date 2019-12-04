package com.baeldung.hexagon.articles.domain.model;

public class AuthorId {
    private final String value;

    private AuthorId(final String value) {
        this.value = value;
    }

    public static AuthorId of(final String authorId) {
        return new AuthorId(authorId);
    }

    public String value() {
        return value;
    }
}
