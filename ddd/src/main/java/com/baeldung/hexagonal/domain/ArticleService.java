package com.baeldung.hexagonal.domain;

import com.baeldung.hexagonal.port.ArticleRepositoryPort;
import com.baeldung.hexagonal.port.ArticleServicePort;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class ArticleService implements ArticleServicePort {

    @Autowired
    private ArticleRepositoryPort articleRepositoryPort;


    @Override
    public void createArticle(Article article) {
        articleRepositoryPort.save(article);
    }

    @Override
    public Article findArticleByName(String name) {
        Optional<Article> articleByName = articleRepositoryPort.findByName(name);
        return articleByName.orElse(null);
    }
}
