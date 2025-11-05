package com.baeldung.verticalslices.recommendation;

import java.util.List;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.baeldung.verticalslices.events.ArticleCreatedEvent;

@Component
class SendArticleRecommendationUseCase {

    @EventListener
    void onArticleRecommendation(ArticleCreatedEvent article) {
        findTargetAudience(article.name(), article.category())
          .forEach(follower -> sendArticleViaEmail(article.slug(), article.name(), follower));
    }

    private void sendArticleViaEmail(String slug, String name, TopicFollower follower) {
        // ...
    }

    private List<TopicFollower> findTargetAudience(String name, String category) {
        // ...
        return List.of();
    }

    record TopicFollower(String email, String name) {}
}
