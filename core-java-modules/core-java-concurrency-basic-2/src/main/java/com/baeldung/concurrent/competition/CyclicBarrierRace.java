package com.baeldung.concurrent.competition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class CyclicBarrierRace implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(CyclicBarrierRace.class.getName());

    public static void main(String[] args) {
        int maxPoolSize = 13000;
        ExecutorService executor = Executors.newFixedThreadPool(maxPoolSize);
        CyclicBarrier barrier = new CyclicBarrier(maxPoolSize, new CyclicBarrierRace());
        log.info("Waiting for runners");
        // The CycleBarrierRace 13000 runners; so wait for 13000 runners
        // (i.e., 13000 threads) to join to start the race
        for (int i = 1; i <= maxPoolSize; i++) {
            executor.execute(new CyclicBarrierRunner(i, barrier));
        }
        executor.shutdown();
    }

    @Override
    public void run() {
        log.info("Starting the race");
    }
}
