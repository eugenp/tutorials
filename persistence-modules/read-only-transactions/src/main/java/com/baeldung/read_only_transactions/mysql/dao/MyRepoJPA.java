package com.baeldung.read_only_transactions.mysql.dao;

import com.baeldung.read_only_transactions.mysql.entities.Transaction;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.SplittableRandom;
import java.util.concurrent.atomic.AtomicLong;

public class MyRepoJPA extends BaseRepo {

    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa-unit");
    private final SplittableRandom random = new SplittableRandom();

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
        entityManager.find(Transaction.class, 1L + random.nextLong(0, 1000000));
        count.incrementAndGet();
        entityManager.clear();
    }

}
