package com.baeldung.concurrent.competition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CyclicBarrier;

class CyclicBarrierRace implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(CyclicBarrierRace.class.getName());

    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(3, new CyclicBarrierRace());
        // The CycleBarrierRace 3 runners; so wait for three runners
        // (i.e., three threads) to join to start the race
        new CyclicBarrierRunner("Eve", 1, barrier).start();
        new CyclicBarrierRunner("Joe", 2, barrier).start();
        new CyclicBarrierRunner("Max", 3, barrier).start();
        // raceWithManyRunners();
    }

    public static void raceWithManyRunners() {
        int max = 10000;
        log.info("Starting the race with {} runners", max);
        CyclicBarrier barrier2 = new CyclicBarrier(max, new CyclicBarrierRace());
        for (int i = 0; i < max; i++) {
            new CyclicBarrierRunner("Runner" + i, i, barrier2).start();
        }
    }

    @Override
    public void run() {
        log.info("Starting the race");
    }
}
