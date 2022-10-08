package com.baeldung.limitrequests.server;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntSupplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Concurrency {

    public static final int MAX_CONCURRENT_REQUESTS = 5;
    private static final Logger LOGGER = LoggerFactory.getLogger(Concurrency.class);
    private static final Map<String, AtomicInteger> CONCURRENT_REQUESTS = new HashMap<>();

    public static int protect(String clientId, IntSupplier supplier) throws InterruptedException {
        AtomicInteger counter = CONCURRENT_REQUESTS.computeIfAbsent(clientId, (k) -> new AtomicInteger(0));

        try {
            int n = counter.incrementAndGet();
            if (n > MAX_CONCURRENT_REQUESTS) {
                throw new UnsupportedOperationException(clientId + " - " + MAX_CONCURRENT_REQUESTS + " max concurrent requests reached [" + n + "]. try again later");
            }

            LOGGER.info(clientId + " - " + n);
            TimeUnit.SECONDS.sleep(2);
            return supplier.getAsInt();
        } finally {
            counter.decrementAndGet();
        }
    }
}
