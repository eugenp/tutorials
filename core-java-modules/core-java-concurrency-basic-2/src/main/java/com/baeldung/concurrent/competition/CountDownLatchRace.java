package com.baeldung.concurrent.competition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

class CountDownLatchRace {
    private static final Logger log = LoggerFactory.getLogger(CountDownLatchRace.class.getName());

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch counter = new CountDownLatch(3);
        // count from 3 to 0 and then start the race
        // instantiate three runner threads
        new CountDownLatchRunner("Eve", 1, counter).start();
        new CountDownLatchRunner("Joe", 2, counter).start();
        new CountDownLatchRunner("Max", 3, counter).start();
        log.info("Starting the countdown");
        while (counter.getCount() > 0) {
            Thread.sleep(1000);
            log.info("{}", counter.getCount());
            if (counter.getCount() == 1) {
                // Count down will reach zero
                log.info("Starting the race");
            }
            counter.countDown(); // count down by 1 for each second
        }
    }
}
