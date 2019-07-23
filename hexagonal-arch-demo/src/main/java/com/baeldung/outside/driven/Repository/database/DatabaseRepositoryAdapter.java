package com.baeldung.outside.driven.Repository.database;

import com.baeldung.outside.driven.Repository.RepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Random;

@Service
public class DatabaseRepositoryAdapter implements RepositoryPort {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public boolean createQuote(String message, String author) {
        try {
            QuoteDBModel quoteDBModel = new QuoteDBModel(message, author);
            entityManager.persist(quoteDBModel);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public QuoteDBModel getQuote() {
        Query countQuery = entityManager.createQuery("select count(*) from quotes");
        long count = (Long) countQuery.getSingleResult();
        if (count <= 0) {
            return null;
        }

        Random random = new Random();
        int number = random.nextInt((int) count) + 1;


        return entityManager.createQuery("SELECT q FROM quotes q WHERE id = :id", QuoteDBModel.class)
                .setParameter("id", (long) number)
                .setMaxResults(1)
                .getSingleResult();
    }


}
