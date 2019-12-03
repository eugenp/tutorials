package com.baeldung.hexagon.articles.domain.ports;

import com.baeldung.hexagon.articles.domain.Article;
import com.baeldung.hexagon.articles.domain.Author;
import com.baeldung.hexagon.articles.domain.AuthorId;

public interface AuthorRepository {

    Author get(AuthorId authorId);
}
