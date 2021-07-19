package com.baeldung.meecrowave;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ArticleService {
    public Article createArticle(Article article) {
        return article;
    }
}
