package com.baeldung.spring.data.jpa.spel.entity;

public class ArticleWrapper {

    private final Article article;

    public ArticleWrapper(Article article) {
        this.article = article;
    }

    public Article getArticle() {
        return article;
    }
}
