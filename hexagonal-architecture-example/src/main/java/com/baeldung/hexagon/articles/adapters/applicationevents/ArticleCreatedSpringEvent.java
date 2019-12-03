package com.baeldung.hexagon.articles.adapters.applicationevents;

import com.baeldung.hexagon.articles.domain.Article;
import com.baeldung.hexagon.articles.domain.events.ArticleCreatedEvent;
import com.baeldung.hexagon.articles.domain.events.ArticleEvent;
import org.springframework.context.ApplicationEvent;

class ArticleCreatedSpringEvent extends ApplicationEvent {

    private final ArticleCreatedEvent articleEvent;

    public ArticleCreatedSpringEvent(final ArticleCreatedEvent articleEvent) {
        super(articleEvent);
        this.articleEvent = articleEvent;
    }

    public Article article() {
        return articleEvent.article();
    }

    public ArticleEvent articleEvent() {
        return articleEvent;
    }
}
