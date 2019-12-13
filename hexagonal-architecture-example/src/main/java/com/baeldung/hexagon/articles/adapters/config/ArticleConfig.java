package com.baeldung.hexagon.articles.adapters.config;

import com.baeldung.hexagon.articles.domain.ArticleFacade;
import com.baeldung.hexagon.articles.domain.ports.ArticleRepository;
import com.baeldung.hexagon.articles.domain.ports.AuthorRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ArticleConfig {

    @Bean
    ArticleFacade articleFacade(final ArticleRepository articleRepository,
                                final AuthorRepository authorRepository) {
        return new ArticleFacade(articleRepository, authorRepository);
    }

}
