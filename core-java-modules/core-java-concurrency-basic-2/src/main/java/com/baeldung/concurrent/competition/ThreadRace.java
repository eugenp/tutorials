package com.baeldung.concurrent.competition;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ThreadRace {
    public static void main(String[] args) {
        int maxPoolSize = 13000;
        ExecutorService executor = Executors.newFixedThreadPool(maxPoolSize);
        for (int i = 1; i <= maxPoolSize; i++) {
            executor.execute(new ThreadRunner(i));
        }
        executor.shutdown();
    }
}
