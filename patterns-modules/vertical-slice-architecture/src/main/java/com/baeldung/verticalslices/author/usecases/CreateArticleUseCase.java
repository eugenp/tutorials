package com.baeldung.verticalslices.author.usecases;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.baeldung.verticalslices.author.domain.AuthorRepository;
import com.baeldung.verticalslices.events.ArticleCreatedEvent;

@Component
public class CreateArticleUseCase {

    private final ApplicationEventPublisher eventPublisher;
    private final AuthorRepository authorRepository;

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

    CreateArticleUseCase(AuthorRepository authorRepository, ApplicationEventPublisher eventPublisher) {
        this.authorRepository = authorRepository;
        this.eventPublisher = eventPublisher;
    }
}
