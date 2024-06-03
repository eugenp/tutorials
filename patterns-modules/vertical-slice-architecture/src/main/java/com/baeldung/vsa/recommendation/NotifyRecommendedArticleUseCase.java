package com.baeldung.vsa.recommendation;

import org.springframework.context.event.EventListener;

import com.baeldung.vsa.events.ArticleRecommendationEvent;

class NotifyRecommendedArticleUseCase {

    @EventListener
    void onArticleRecommendation(ArticleRecommendationEvent article) {

    }
}
