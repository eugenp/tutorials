package com.baeldung.meecrowave;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ArticleService {
    public Article createArticle(Article article) {
        return article;
    }
}
