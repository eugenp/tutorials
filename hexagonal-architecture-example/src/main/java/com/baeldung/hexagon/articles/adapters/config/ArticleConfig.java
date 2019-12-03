package com.baeldung.hexagon.articles.adapters.config;

import com.baeldung.hexagon.articles.domain.ArticleFacade;
import com.baeldung.hexagon.articles.domain.ports.ArticleApplicationEventPublisher;
import com.baeldung.hexagon.articles.domain.ports.ArticleRepository;
import com.baeldung.hexagon.articles.domain.ports.AuthorRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ArticleConfig {

    @Bean
    ArticleFacade articleFacade(final ArticleApplicationEventPublisher eventPublisher,
                                final ArticleRepository articleRepository,
                                final AuthorRepository authorRepository) {
        return new ArticleFacade(eventPublisher, articleRepository, authorRepository);
    }
}
