package com.baeldung.concurrent.countdownlatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Worker implements Runnable {

    private static Logger log = LoggerFactory.getLogger(Worker.class);
    private final List<String> outputScraper;
    private final CountDownLatch countDownLatch;

    Worker(final List<String> outputScraper, final CountDownLatch countDownLatch) {
        this.outputScraper = outputScraper;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        // Do some work
        log.debug("Doing some logic");
        outputScraper.add("Counted down");
        countDownLatch.countDown();
    }
}
