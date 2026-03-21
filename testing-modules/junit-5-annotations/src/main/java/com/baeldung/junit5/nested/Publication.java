package com.baeldung.junit5.nested;

import java.util.List;
import java.util.stream.Collectors;

public class Publication {
    private final List<Article> articles;

    public Publication(List<Article> articles) {
        this.articles = articles;
    }

    public List<String> getReadableArticles(User user) {
        return articles.stream()
            .filter(a -> a.getArticleLevel()
              .compare(user.getMembership()) <= 0)
            .map(Article::getName)
            .collect(Collectors.toList());
    }

    public List<String> getLockedArticles(User user) {
        return articles.stream()
            .filter(a -> a.getArticleLevel()
              .compare(user.getMembership()) > 0)
            .map(Article::getName)
            .collect(Collectors.toList());
    }

    public List<Article> getArticles() {
        return articles;
    }
}