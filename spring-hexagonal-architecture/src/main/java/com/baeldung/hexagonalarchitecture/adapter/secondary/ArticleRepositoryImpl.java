package com.baeldung.hexagonalarchitecture.adapter.secondary;

import com.baeldung.hexagonalarchitecture.hexagon.domain.Article;
import com.baeldung.hexagonalarchitecture.port.driven.ArticleRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class ArticleRepositoryImpl implements ArticleRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Article> findAll() {
        return entityManager.createQuery("FROM Article", Article.class).getResultList();
    }

    @Override
    public Article addArticle(Article article) {
        entityManager.persist(article);
        return article;
    }

    @Override
    public Optional<Article> findOne(long id) {
        return Optional.of(entityManager.find(Article.class, id));
    }
}
