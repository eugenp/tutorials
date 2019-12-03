package com.baeldung.hexagon.articles.domain.events;

import com.baeldung.hexagon.articles.domain.Article;

public class ArticleFetchedEvent implements ArticleEvent {

    private final Article article;

    public ArticleFetchedEvent(final Article article) {
        this.article = article;
    }

    @Override
    public Article article() {
        return article;
    }
}
