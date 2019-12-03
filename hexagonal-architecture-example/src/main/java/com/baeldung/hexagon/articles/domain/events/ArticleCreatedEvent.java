package com.baeldung.hexagon.articles.domain.events;

import com.baeldung.hexagon.articles.domain.Article;

public class ArticleCreatedEvent implements ArticleEvent {
    private final Article article;

    public  ArticleCreatedEvent(final Article article) {
        this.article = article;
    }

    @Override
    public Article article() {
        return article;
    }

}
