package com.baeldung.concurrent.competition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class CountDownLatchRace {
    private static final Logger log = LoggerFactory.getLogger(CountDownLatchRace.class.getName());

    public static void main(String[] args) {
        int maxPoolSize = 13000;
        ExecutorService executor = Executors.newFixedThreadPool(maxPoolSize);
        CountDownLatch counter = new CountDownLatch(maxPoolSize);
        log.info("Starting the countdown");
        for (int i = 1; i <= maxPoolSize; i++) {
            executor.execute(new CountDownLatchRunner(i, counter));
            log.info("{}", counter.getCount());
            if (counter.getCount() == 1) {
                // Count down will reach zero
                log.info("Starting the race");
            }
            counter.countDown(); // count down by 1 for each runner join to the race
        }
        executor.shutdown();
    }
}
