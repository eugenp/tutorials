package com.baeldung.joseanibalrodpez.hexagonalarchitecture.domain.repository;

import com.baeldung.joseanibalrodpez.hexagonalarchitecture.domain.model.Article;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository {
    Article getArticle(Long id);

    List<Article> getArticles();

    Article createArticle(Article article);

    Article updateArticle(Long id, Article article);

    Article deleteArticle(Long id);
}
