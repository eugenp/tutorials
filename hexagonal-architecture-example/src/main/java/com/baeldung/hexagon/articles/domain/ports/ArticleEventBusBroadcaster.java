package com.baeldung.hexagon.articles.domain.ports;

import com.baeldung.hexagon.articles.domain.events.ArticleEvent;

public interface ArticleEventBusBroadcaster {

    void broadcast(ArticleEvent articleEvent);

}
