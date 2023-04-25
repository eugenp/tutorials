package com.baeldung.concurrent.phaser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Phaser;

class LongRunningAction implements Runnable {

    private static Logger log = LoggerFactory.getLogger(LongRunningAction.class);
    private String threadName;
    private Phaser ph;

    LongRunningAction(String threadName, Phaser ph) {
        this.threadName = threadName;
        this.ph = ph;
        ph.register();
    }

    @Override
    public void run() {
        log.info("This is phase {}", ph.getPhase());
        log.info("Thread {} before long running action", threadName);
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.debug("Thread {} action completed and waiting for others", threadName);
        ph.arriveAndAwaitAdvance();
        log.debug("Thread {} proceeding in phase {}", threadName, ph.getPhase());
        
        ph.arriveAndDeregister();
    }
}