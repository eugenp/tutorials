package com.baeldung.logging.log4j2.threadinfo;

import org.apache.logging.log4j.ThreadContext;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;

public class ApplicationThread extends Thread {
    private final User user;

    public ApplicationThread(User user) {
        this.user = user;
        user.connect();
    }

    @Override
    public void run() {
        ThreadContext.put("name", user.getName());
        BooleanSupplier poll;

        do {
            poll = user.actionPoll();

            sleepRandom();
        } while (poll == null || poll.getAsBoolean());
    }

    private void sleepRandom() {
        try {
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
