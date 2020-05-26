package com.baeldung.hexagonal.port;

import com.baeldung.hexagonal.domain.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepositoryPort {

    Article save(Article article);

    Optional<Article> findByName(String name);

    List<Article> findAll();
}
