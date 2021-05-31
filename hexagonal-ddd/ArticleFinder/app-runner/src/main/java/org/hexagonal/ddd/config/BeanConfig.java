package org.hexagonal.ddd.config;

import org.hexagonal.dd.db.ArticleDAOImpl;
import org.hexagonal.ddd.domain.ports.ext.IArticleAPI;
import org.hexagonal.ddd.domain.ports.infra.IArticleDAO;
import org.hexagonal.ddd.domain.service.ArticleAPIImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Bean
    public IArticleDAO getArticleDAO() {
        return new ArticleDAOImpl();
    }

    @Bean
    public IArticleAPI getArticleAPI() {
        return new ArticleAPIImpl(getArticleDAO());
    }
}
