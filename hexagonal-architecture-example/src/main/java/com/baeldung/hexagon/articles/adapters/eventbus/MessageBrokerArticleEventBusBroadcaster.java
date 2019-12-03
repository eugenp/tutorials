package com.baeldung.hexagon.articles.adapters.eventbus;

import com.baeldung.hexagon.articles.domain.events.ArticleEvent;
import com.baeldung.hexagon.articles.domain.ports.ArticleEventBusBroadcaster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
class MessageBrokerArticleEventBusBroadcaster implements ArticleEventBusBroadcaster {

    private final Logger log = LoggerFactory.getLogger(MessageBrokerArticleEventBusBroadcaster.class);

    @Override
    public void broadcast(final ArticleEvent articleEvent) {
        /**
         * message broker integration implementation using {@link ArticleMessageBrokerModel} comes here
         */
        log.info("Article event broadcast on event bus {}", articleEvent.getClass().getSimpleName());
    }
}
