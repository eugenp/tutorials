package com.baeldung.concurrent.stopexecution;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class FixedTimeTask implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(FixedTimeTask.class);

    final int fixedTime; // milliseconds

    public FixedTimeTask(int fixedTime) {
        this.fixedTime = fixedTime;
    }

    @Override
    public void run() {
        LOG.info(fixedTime + " milliseconds running task");
        try {
            TimeUnit.MILLISECONDS.sleep(fixedTime);
        } catch (InterruptedException e) {
            LOG.info("interrupted");
        }
        LOG.info("finished");
    }
}
