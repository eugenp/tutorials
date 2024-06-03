package com.baeldung.hexagonal.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.baeldung.hexagonal.persistence.entity.Article;
import com.baeldung.hexagonal.persistence.repository.ArticleRepository;

@Service
public class ArticleService {

    private final ArticleRepository repository;

    public ArticleService(ArticleRepository repository) {
        this.repository = repository;
    }

    public Article create(Article article) {
        return repository.save(article);
    }

    public Optional<Article> findById(Long id) {
        return repository.findById(id);
    }

    public Article update(Long articleId, String newContent) {
        var article = findById(articleId).orElseThrow();
        article.setContent(newContent);
        return repository.save(article);
    }
}
