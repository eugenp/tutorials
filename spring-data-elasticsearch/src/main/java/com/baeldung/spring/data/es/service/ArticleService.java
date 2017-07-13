package com.baeldung.spring.data.es.service;

import com.baeldung.spring.data.es.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleService {
    Article save(Article article);

    Article findOne(String id);

    Iterable<Article> findAll();

    Page<Article> findByAuthorName(String name, Pageable pageable);

    Page<Article> findByAuthorNameUsingCustomQuery(String name, Pageable pageable);

    long count();

    void delete(Article article);
}
