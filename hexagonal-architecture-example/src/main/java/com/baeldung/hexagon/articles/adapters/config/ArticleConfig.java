package com.baeldung.hexagon.articles.adapters.config;

import com.baeldung.hexagon.articles.domain.ArticleFacade;
import com.baeldung.hexagon.articles.domain.ports.ArticleAuthorNotifier;
import com.baeldung.hexagon.articles.domain.ports.ArticleEventPublisher;
import com.baeldung.hexagon.articles.domain.ports.ArticleRepository;
import com.baeldung.hexagon.articles.domain.ports.AuthorRepository;
import com.baeldung.hexagon.articles.domain.ports.SocialMediaPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
class ArticleConfig {

    @Bean
    ArticleFacade articleFacade(final ArticleRepository articleRepository,
                                final AuthorRepository authorRepository,
                                final ArticleEventPublisher eventPublisher,
                                final List<SocialMediaPublisher> socialMediaPublishers,
                                final List<ArticleAuthorNotifier> articleAuthorNotifiers) {
        return new ArticleFacade(eventPublisher, articleRepository, authorRepository, socialMediaPublishers, articleAuthorNotifiers);
    }

}
