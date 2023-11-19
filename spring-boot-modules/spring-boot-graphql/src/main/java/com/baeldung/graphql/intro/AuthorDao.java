package com.baeldung.graphql.intro;

import java.util.List;

public class AuthorDao {
    private final List<Author> authors;

    public AuthorDao(List<Author> authors) {
        this.authors = authors;
    }

    public Author getAuthor(String id) {
        return authors.stream()
          .filter(author -> id.equals(author.getId()))
          .findFirst()
          .orElseThrow(RuntimeException::new);
    }
}
