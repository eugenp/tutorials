package com.baeldung.joseanibalrodpez.hexagonalarchitecture.domain.repository;

import com.baeldung.joseanibalrodpez.hexagonalarchitecture.domain.model.Article;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class DefaultArticleRepository implements ArticleRepository {

    private final EntityManager entityManager;

    public DefaultArticleRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Article getArticle(Long id) {
        // Returns the stored article
        return entityManager.find(Article.class, id);
    }

    @Override
    public List<Article> getArticles() {
        // Returns the list of stored articles
        return entityManager.createQuery("SELECT A FROM Article A", Article.class).getResultList();
    }

    @Override
    @Transactional
    public Article createArticle(Article article) {
        // Persists the requested entity
        this.entityManager.persist(article);
        // Returns the stored article
        return article;
    }

    @Override
    @Modifying
    @Transactional
    public Article updateArticle(Long id, Article article) {
        // Loads the stored article
        final Article stored = getArticle(id);
        // Updates the fields values
        stored.setName(article.getName());
        stored.setEditor(article.getEditor());
        stored.setContent(article.getContent());
        // Persists the requested entity
        entityManager.persist(stored);
        // Returns the stored article
        return stored;
    }

    @Override
    @Modifying
    @Transactional
    public Article deleteArticle(Long id) {
        // Loads the stored article
        final Article article = getArticle(id);
        // Remove the article from db
        entityManager.remove(article);
        // Returns the deleted item
        return article;
    }
}
