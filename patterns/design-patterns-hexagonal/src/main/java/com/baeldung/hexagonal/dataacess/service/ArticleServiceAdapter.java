package com.baeldung.hexagonal.dataacess.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import com.baeldung.hexagonal.dataacess.interfaces.IArticleRepositoryPort;
import com.baeldung.hexagonal.dataccess.entities.ArticleModel;

@Service
public class ArticleServiceAdapter implements IArticleRepositoryPort {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ArticleModel getArticle(Integer articleId) {
    	return entityManager.find(ArticleModel.class, articleId);
    }
}