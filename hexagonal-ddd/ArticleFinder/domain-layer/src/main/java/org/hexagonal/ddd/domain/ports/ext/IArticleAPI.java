package org.hexagonal.ddd.domain.ports.ext;

import org.hexagonal.ddd.domain.Article;

import java.util.List;

public interface IArticleAPI {
    String addArticle(final Article article);

    String deleteArticle(final String id);

    String updateArticle(final Article article);

    List<Article> getArticles();

    Article getArticleById(String id);
}
