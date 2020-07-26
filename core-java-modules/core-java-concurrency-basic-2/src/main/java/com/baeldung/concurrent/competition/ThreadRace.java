package com.baeldung.concurrent.competition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

class ThreadRace {
    private static final Logger log = LoggerFactory.getLogger(ThreadRace.class.getName());

    public static void main(String[] args) {
        Thread runner1 = new ThreadRunner("Eve", 1);
        Thread runner2 = new ThreadRunner("Joe", 2);
        Thread runner3 = new ThreadRunner("Max", 3);
        log.info("Starting the race");
        runner1.start();
        runner2.start();
        runner3.start();
        // raceWithManyRunners();
    }

    public static void raceWithManyRunners() {
        int max = 10000;
        log.info("Starting the race with {} runners", max);
        List<ThreadRunner> runners = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            runners.add(new ThreadRunner("Runner" + i, i));
        }
        runners.forEach(Thread::start);
    }
}
