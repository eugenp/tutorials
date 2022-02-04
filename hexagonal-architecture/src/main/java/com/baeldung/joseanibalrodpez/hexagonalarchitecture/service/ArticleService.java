package com.baeldung.joseanibalrodpez.hexagonalarchitecture.service;

import com.baeldung.joseanibalrodpez.hexagonalarchitecture.domain.model.Article;

import java.util.List;

public interface ArticleService {

    Article getArticle(Long id);

    List<Article> getArticles();

    Article createArticle(Article article);

    Article updateArticle(Long id, Article article);

    Article deleteArticle(Long id);
}
