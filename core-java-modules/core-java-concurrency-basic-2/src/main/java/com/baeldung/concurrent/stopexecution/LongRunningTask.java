package com.baeldung.concurrent.stopexecution;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LongRunningTask implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(LongRunningTask.class);

    @Override
    public void run() {
        LOG.info("running");
        for (int i = 0; i < Long.MAX_VALUE; i++) {
            if (Thread.interrupted()) {
                LOG.info("stopping");
                return;
            }
        }
        LOG.info("finished");
    }
}
