package com.baeldung.hexagon.articles.domain;

import com.baeldung.hexagon.articles.domain.model.Article;
import com.baeldung.hexagon.articles.domain.model.ArticleId;
import com.baeldung.hexagon.articles.domain.model.Author;
import com.baeldung.hexagon.articles.domain.model.AuthorId;
import com.baeldung.hexagon.articles.domain.model.Content;
import com.baeldung.hexagon.articles.domain.model.Title;
import com.baeldung.hexagon.articles.domain.ports.ArticleAuthorNotifier;
import com.baeldung.hexagon.articles.domain.ports.ArticleEventPublisher;
import com.baeldung.hexagon.articles.domain.ports.ArticleRepository;
import com.baeldung.hexagon.articles.domain.ports.AuthorRepository;
import com.baeldung.hexagon.articles.domain.ports.SocialMediaPublisher;

import java.util.List;

public class ArticleFacade {

    private final ArticleEventPublisher eventPublisher;
    private final ArticleRepository articleRepository;
    private final AuthorRepository authorRepository;
    private final List<SocialMediaPublisher> socialMediaPublishers;
    private final List<ArticleAuthorNotifier> articleAuthorNotifiers;

    public ArticleFacade(final ArticleEventPublisher eventPublisher, final ArticleRepository articleRepository, final AuthorRepository authorRepository, final List<SocialMediaPublisher> socialMediaPublishers, final List<ArticleAuthorNotifier> articleAuthorNotifiers) {
        this.eventPublisher = eventPublisher;
        this.articleRepository = articleRepository;
        this.authorRepository = authorRepository;
        this.socialMediaPublishers = socialMediaPublishers;
        this.articleAuthorNotifiers = articleAuthorNotifiers;
    }

    public ArticleId create(final AuthorId authorId, final Title title, final Content content) {
        final Author author = authorRepository.get(authorId);
        final Article article = articleRepository.save(author, title, content);
        eventPublisher.publishArticleCreationEvent(article);
        socialMediaPublishers.forEach(socialMediaPublisher -> socialMediaPublisher.publish(article));
        articleAuthorNotifiers.forEach(articleAuthorNotifier -> articleAuthorNotifier.notifyAboutArticleCreation(article));
        return article.id();
    }

    public Article get(final ArticleId id) {
        final Article article = articleRepository.get(id);
        eventPublisher.publishArticleRetrievalEvent(article);
        return article;
    }
}
