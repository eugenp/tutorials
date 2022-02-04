package com.baeldung.joseanibalrodpez.hexagonalarchitecture.service;

import com.baeldung.joseanibalrodpez.hexagonalarchitecture.domain.model.Article;
import com.baeldung.joseanibalrodpez.hexagonalarchitecture.domain.repository.ArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DefaultArticleService implements ArticleService {
    private final ArticleRepository articleRepository;

    public DefaultArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public Article getArticle(Long id) {
        return this.articleRepository.getArticle(id);
    }

    @Override
    public List<Article> getArticles() {
        return this.articleRepository.getArticles();
    }

    @Override
    @Transactional
    public Article createArticle(Article article) {
        return this.articleRepository.createArticle(article);
    }

    @Override
    @Transactional
    public Article updateArticle(Long id, Article article) {
        return this.articleRepository.updateArticle(id, article);
    }

    @Override
    public Article deleteArticle(Long id) {
        return articleRepository.deleteArticle(id);
    }
}
