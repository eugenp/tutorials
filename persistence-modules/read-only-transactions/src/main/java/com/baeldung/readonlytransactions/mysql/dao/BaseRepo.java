package com.baeldung.readonlytransactions.mysql.dao;

import com.baeldung.readonlytransactions.utils.ExecutorUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

public abstract class BaseRepo {

    protected long execQuery(Consumer<AtomicLong> function) {
        AtomicLong count = new AtomicLong(0);

        ExecutorService executor = ExecutorUtils.createExecutor(10, 10);
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        scheduler.schedule(executor::shutdownNow, 5L, TimeUnit.SECONDS);
        scheduler.shutdown();

        while (!executor.isShutdown()) {
            executor.execute(() -> function.accept(count));
        }

        return count.get();
    }
}
