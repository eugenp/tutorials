package com.baeldung.verticalslices.recommendation;

import org.springframework.context.event.EventListener;

import com.baeldung.verticalslices.events.ArticleRecommendationEvent;

class SendArticleRecommendationUseCase {

    @EventListener
    void onArticleRecommendation(ArticleRecommendationEvent article) {

    }
}
