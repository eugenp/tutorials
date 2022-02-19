package com.baeldung.read_only_transactions.mysql.dao;

import com.baeldung.read_only_transactions.mysql.entities.Transaction;
import com.baeldung.read_only_transactions.utils.ExecutorUtils;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.SplittableRandom;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class MyRepoJPA {

    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa-unit");
    private final SplittableRandom random = new SplittableRandom();

    public long runQuery() {
        AtomicLong count = new AtomicLong(0);

        ExecutorService executor = ExecutorUtils.createExecutor(10, 10);
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        scheduler.schedule(executor::shutdownNow, 5L, TimeUnit.SECONDS);
        scheduler.shutdown();

        while (true) {
            if (executor.isShutdown()) {
                break;
            }

            executor.execute(() -> runSql(count));
        }

        return count.get();
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
