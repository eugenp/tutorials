package com.baeldung.hexagon.articles.domain.ports;

import com.baeldung.hexagon.articles.domain.events.ArticleCreatedEvent;
import com.baeldung.hexagon.articles.domain.events.ArticleFetchedEvent;

public interface ArticleApplicationEventPublisher {

    void publish(final ArticleFetchedEvent articleEvent);

    void publish(final ArticleCreatedEvent articleEvent);

}
