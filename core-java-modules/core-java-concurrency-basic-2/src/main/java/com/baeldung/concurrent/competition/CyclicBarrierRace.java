package com.baeldung.concurrent.competition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CyclicBarrier;

class CyclicBarrierRace implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(CyclicBarrierRace.class.getName());

    public static void main(String[] args) throws InterruptedException {
        CyclicBarrier barrier = new CyclicBarrier(3, new CyclicBarrierRace());
        // The CycleBarrierRace 3 runners; so wait for three runners
        // (i.e., three threads) to join to start the race
        new CyclicBarrierRunner("Eva", 1, barrier).start();
        new CyclicBarrierRunner("Joe", 2, barrier).start();
        new CyclicBarrierRunner("Mario", 3, barrier).start();
    }

    @Override
    public void run() {
        log.info("Starting the race");
    }
}
