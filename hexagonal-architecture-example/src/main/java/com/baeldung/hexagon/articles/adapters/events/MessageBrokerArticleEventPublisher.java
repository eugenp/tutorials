package com.baeldung.hexagon.articles.adapters.events;

import com.baeldung.hexagon.articles.domain.model.Article;
import com.baeldung.hexagon.articles.domain.ports.ArticleEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
class MessageBrokerArticleEventPublisher implements ArticleEventPublisher {

    private final Logger log = LoggerFactory.getLogger(MessageBrokerArticleEventPublisher.class);

    @Override
    public void publishArticleCreationEvent(final Article article) {
        /**
         * message broker integration implementation using {@link ArticleCreatedEvent} comes here
         */
        log.info("Article: \"{}\" creation event published on event bus", article.title().value());
    }

    @Override
    public void publishArticleRetrievalEvent(final Article article) {
        /**
         * message broker integration implementation using {@link ArticleRetrievedEvent} comes here
         */
        log.info("Article: \"{}\" retrieval event published on event bus", article.title().value());
    }
}
