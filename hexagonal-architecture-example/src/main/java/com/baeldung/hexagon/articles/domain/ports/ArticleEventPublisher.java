package com.baeldung.hexagon.articles.domain.ports;

import com.baeldung.hexagon.articles.domain.model.Article;

public interface ArticleEventPublisher {

    void publishArticleCreationEvent(Article article);

    void publishArticleRetrievalEvent(Article article);

}
