package com.baeldung.parallel_collectors;

import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;

public class ParallelCollectorsVirtualThreadsUnitTest {

    @Test
    public void todo() {
    }

    private static String fetchById(int id) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            // ignore shamelessly
        }

        return "user-" + id;
    }

    private static String fetchByIdWithRandomDelay(int id) {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000));
        } catch (InterruptedException e) {
            // ignore shamelessly
        }

        return "user-" + id;
    }
}
