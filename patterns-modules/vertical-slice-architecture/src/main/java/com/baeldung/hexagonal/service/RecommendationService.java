package com.baeldung.hexagonal.service;

public class RecommendationService {
    private final ArticleService articleService;

    public RecommendationService(ArticleService articleService) {
        this.articleService = articleService;
    }
}
