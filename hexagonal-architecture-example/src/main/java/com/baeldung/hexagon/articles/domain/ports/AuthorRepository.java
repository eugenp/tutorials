package com.baeldung.hexagon.articles.domain.ports;

import com.baeldung.hexagon.articles.domain.model.Author;

public interface AuthorRepository {

    Author get(String authorId);
}
