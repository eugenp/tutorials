package com.baeldung.hexagonal.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.baeldung.hexagonal.dto.ArticleDto;
import com.baeldung.hexagonal.persistence.entity.Article;
import com.baeldung.hexagonal.persistence.repository.ArticleRepository;

@Service
public class ArticleService {

    private final ArticleRepository repository;
    private final CommentService commentService;
    private final RecommendationService recommendationService;
    private final UserService userService;

    public ArticleService(ArticleRepository repository, CommentService commentService, RecommendationService recommendationService, UserService userService) {
        this.repository = repository;
        this.commentService = commentService;
        this.recommendationService = recommendationService;
        this.userService = userService;
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
