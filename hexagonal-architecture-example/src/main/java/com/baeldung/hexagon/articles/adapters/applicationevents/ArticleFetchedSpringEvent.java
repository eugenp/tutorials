package com.baeldung.hexagon.articles.adapters.applicationevents;

import com.baeldung.hexagon.articles.domain.Article;
import com.baeldung.hexagon.articles.domain.events.ArticleEvent;
import com.baeldung.hexagon.articles.domain.events.ArticleFetchedEvent;
import org.springframework.context.ApplicationEvent;

class ArticleFetchedSpringEvent extends ApplicationEvent {

    private final ArticleFetchedEvent articleEvent;

    public ArticleFetchedSpringEvent(final ArticleFetchedEvent articleEvent) {
        super(articleEvent);
        this.articleEvent = articleEvent;
    }

    public Article article() {
        return articleEvent.article();
    }

    ArticleEvent articleEvent() {
        return articleEvent;
    }
}
