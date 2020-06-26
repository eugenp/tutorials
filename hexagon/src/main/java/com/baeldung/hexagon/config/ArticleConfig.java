package com.baeldung.hexagon.config;


import com.baeldung.hexagon.repository.ArticleRepository;
import com.baeldung.hexagon.service.ArticleService;
import com.baeldung.hexagon.service.IArticleService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ArticleConfig {

    @Bean
    @ConfigurationProperties(prefix = "articles")
    public ArticleService.Properties properties() {
        return new ArticleService.Properties();
    }

    @Bean
    public IArticleService articleService(ArticleRepository repository, ArticleService.Properties properties) {
        return new ArticleService(repository, properties);
    }
}
