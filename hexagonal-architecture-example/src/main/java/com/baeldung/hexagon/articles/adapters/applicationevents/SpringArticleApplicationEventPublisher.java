package com.baeldung.hexagon.articles.adapters.applicationevents;

import com.baeldung.hexagon.articles.domain.events.ArticleCreatedEvent;
import com.baeldung.hexagon.articles.domain.events.ArticleFetchedEvent;
import com.baeldung.hexagon.articles.domain.ports.ArticleApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
class SpringArticleApplicationEventPublisher implements ArticleApplicationEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    SpringArticleApplicationEventPublisher(final ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }


    @Override
    public void publish(final ArticleFetchedEvent articleEvent) {
        applicationEventPublisher.publishEvent(new ArticleFetchedSpringEvent(articleEvent));
    }

    @Override
    public void publish(final ArticleCreatedEvent articleEvent) {
        applicationEventPublisher.publishEvent(new ArticleCreatedSpringEvent(articleEvent));
    }
}
