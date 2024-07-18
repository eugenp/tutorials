package com.baeldung.verticalslices.author.usecases;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.baeldung.verticalslices.events.ArticleCreatedEvent;

@Component
class CreateArticleUseCase {

    private final ApplicationEventPublisher eventPublisher;

    void createArticle(CreateArticleRequest article) {
        saveToDatabase(article);

        var event = new ArticleCreatedEvent(article.slug(), article.name(), article.category());
        eventPublisher.publishEvent(event);
    }

    private void saveToDatabase(CreateArticleRequest article) {
        // saving
    }

    record CreateArticleRequest(String slug, String name, String category) {

    }

    CreateArticleUseCase(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }
}
