package com.baeldung.hexagon.articles.domain.ports;

import com.baeldung.hexagon.articles.domain.model.Author;
import com.baeldung.hexagon.articles.domain.model.AuthorId;

public interface AuthorRepository {

    Author get(AuthorId authorId);
}
