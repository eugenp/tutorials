package com.baeldung.hexagon.articles.domain;

import com.baeldung.hexagon.articles.domain.events.ArticleCreatedEvent;
import com.baeldung.hexagon.articles.domain.events.ArticleFetchedEvent;
import com.baeldung.hexagon.articles.domain.ports.ArticleApplicationEventPublisher;
import com.baeldung.hexagon.articles.domain.ports.ArticleRepository;
import com.baeldung.hexagon.articles.domain.ports.AuthorRepository;

public class ArticleFacade {


    private final ArticleApplicationEventPublisher eventPublisher;
    private final ArticleRepository articleRepository;
    private final AuthorRepository authorRepository;

    public ArticleFacade(final ArticleApplicationEventPublisher eventPublisher, final ArticleRepository articleRepository, final AuthorRepository authorRepository) {
        this.eventPublisher = eventPublisher;
        this.articleRepository = articleRepository;
        this.authorRepository = authorRepository;
    }

    public ArticleId create(final AuthorId authorId, final Title title, final Content content) {
        Author author = authorRepository.get(authorId);
        Article article = articleRepository.save(author, title, content);
        eventPublisher.publish(new ArticleCreatedEvent(article));
        return article.id();
    }

    public Article get(final ArticleId id) {
        Article article = articleRepository.get(id);
        eventPublisher.publish(new ArticleFetchedEvent(article));
        return article;
    }
}
