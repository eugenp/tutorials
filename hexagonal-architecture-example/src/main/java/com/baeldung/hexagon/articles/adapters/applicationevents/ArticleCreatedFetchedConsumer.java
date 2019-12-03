package com.baeldung.hexagon.articles.adapters.applicationevents;

import com.baeldung.hexagon.articles.domain.ports.ArticleEventBusBroadcaster;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
class ArticleCreatedFetchedConsumer implements ApplicationListener<ArticleFetchedSpringEvent> {
    private final ArticleEventBusBroadcaster articleEventBusBroadcaster;

    ArticleCreatedFetchedConsumer(final ArticleEventBusBroadcaster articleEventBusBroadcaster) {
        this.articleEventBusBroadcaster = articleEventBusBroadcaster;
    }

    @Override
    public void onApplicationEvent(final ArticleFetchedSpringEvent event) {
        articleEventBusBroadcaster.broadcast(event.articleEvent());
    }

}
