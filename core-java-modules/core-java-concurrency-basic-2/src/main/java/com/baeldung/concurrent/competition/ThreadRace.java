package com.baeldung.concurrent.competition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ThreadRace {
    private static final Logger log = LoggerFactory.getLogger(ThreadRace.class.getName());

    public static void main(String[] args) {
        Thread runner1 = new ThreadRunner("Eva", 1);
        Thread runner2 = new ThreadRunner("Joe", 2);
        Thread runner3 = new ThreadRunner("Mario", 3);
        log.info("Starting the race");
        runner1.start();
        runner2.start();
        runner3.start();
    }
}
