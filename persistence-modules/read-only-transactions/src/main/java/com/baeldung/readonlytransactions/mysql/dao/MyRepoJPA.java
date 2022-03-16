package com.baeldung.readonlytransactions.mysql.dao;

import org.hibernate.Session;

import com.baeldung.readonlytransactions.mysql.entities.Book;

import java.util.SplittableRandom;
import java.util.concurrent.atomic.AtomicLong;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class MyRepoJPA extends BaseRepo {

    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa-unit");
    private SplittableRandom random = new SplittableRandom();

    public long runQuery() {
        return execQuery(this::runSql);
    }

    private void runSql(AtomicLong count) {
        if (Thread.interrupted()) {
            return;
        }

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Session session = entityManager.unwrap(Session.class);
        session.setDefaultReadOnly(true);
        entityManager.find(Book.class, 1L + random.nextLong(0, 1000000));
        count.incrementAndGet();
        entityManager.clear();
    }

}
