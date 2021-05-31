package org.hexagonal.ddd.domain.service;

import org.hexagonal.ddd.domain.Article;
import org.hexagonal.ddd.domain.ports.ext.IArticleAPI;
import org.hexagonal.ddd.domain.ports.infra.IArticleDAO;

import java.util.List;

public class ArticleAPIImpl implements IArticleAPI {

    private final IArticleDAO articleDAO;

    public ArticleAPIImpl(final IArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }

    @Override
    public String addArticle(Article article) {
        return this.articleDAO.addArticle(article);
    }

    @Override
    public String deleteArticle(String id) {
        return this.articleDAO.deleteArticle(id);
    }

    @Override
    public String updateArticle(Article article) {
        return this.articleDAO.updateArticle(article);
    }

    @Override
    public List<Article> getArticles() {
        return this.articleDAO.getArticles();
    }

    @Override
    public Article getArticleById(String id) {
        return this.getArticleById(id);
    }
}
