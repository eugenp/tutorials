package com.baeldung.read_only_transactions.mysql.dao;

import com.baeldung.read_only_transactions.mysql.spring.repositories.TransactionRepository;
import com.baeldung.read_only_transactions.utils.ExecutorUtils;

import java.util.SplittableRandom;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class MyRepoSpring {

    private TransactionRepository repository;

    public MyRepoSpring(TransactionRepository repository) {
        this.repository = repository;
    }

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

            executor.execute(() -> {
                repository.get(1L + random.nextLong(0, 1000000));
                count.incrementAndGet();
            });
        }

        return count.get();
    }
}
