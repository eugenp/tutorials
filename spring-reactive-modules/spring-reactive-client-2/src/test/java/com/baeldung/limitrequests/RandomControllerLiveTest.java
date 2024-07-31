package com.baeldung.limitrequests;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException.InternalServerError;

import com.baeldung.limitrequests.client.DelayElements;
import com.baeldung.limitrequests.client.GuavaRateLimit;
import com.baeldung.limitrequests.client.LimitConcurrency;
import com.baeldung.limitrequests.client.Resilience4jRateLimit;
import com.baeldung.limitrequests.client.ZipWithInterval;
import com.baeldung.limitrequests.server.Concurrency;

class RandomControllerLiveTest {

    private static final int MAX_CONCURRENT_REQUESTS = Concurrency.MAX_CONCURRENT_REQUESTS;
    private static final int TOTAL_REQUESTS = 10;

    private WebClient client = WebClient.create("http://localhost:8081/random");

    @Test
    void givenEnoughDelay_whenZipWithInterval_thenNoExceptionThrown() {
        int delay = MAX_CONCURRENT_REQUESTS * 100;

        assertDoesNotThrow(() -> {
            ZipWithInterval.fetch(client, TOTAL_REQUESTS, delay)
                .doOnNext(System.out::println)
                .blockLast();
        });
    }

    @Test
    void givenSmallDelay_whenZipWithInterval_thenExceptionThrown() {
        int delay = 100;

        assertThrows(InternalServerError.class, () -> {
            ZipWithInterval.fetch(client, TOTAL_REQUESTS, delay)
                .doOnNext(System.out::println)
                .blockLast();
        });
    }

    @Test
    void givenEnoughDelay_whenDelayElements_thenNoExceptionThrown() {
        int delay = MAX_CONCURRENT_REQUESTS * 100;

        assertDoesNotThrow(() -> {
            DelayElements.fetch(client, TOTAL_REQUESTS, delay)
                .doOnNext(System.out::println)
                .blockLast();
        });
    }

    @Test
    void givenSmallDelay_whenDelayElements_thenExceptionThrown() {
        int delay = 100;

        assertThrows(InternalServerError.class, () -> {
            DelayElements.fetch(client, TOTAL_REQUESTS, delay)
                .doOnNext(System.out::println)
                .blockLast();
        });
    }

    @Test
    void givenLimitInsideServerRange_whenLimitedConcurrency_thenNoExceptionThrown() {
        int limit = MAX_CONCURRENT_REQUESTS;

        assertDoesNotThrow(() -> {
            LimitConcurrency.fetch(client, TOTAL_REQUESTS, limit)
                .doOnNext(System.out::println)
                .blockLast();
        });
    }

    @Test
    void givenLimitOutsideServerRange_whenLimitedConcurrency_thenExceptionThrown() {
        int limit = MAX_CONCURRENT_REQUESTS + TOTAL_REQUESTS;

        assertThrows(InternalServerError.class, () -> {
            LimitConcurrency.fetch(client, TOTAL_REQUESTS, limit)
                .doOnNext(System.out::println)
                .blockLast();
        });
    }

    @Test
    void givenLongInterval_whenRateLimited_thenNoExceptionThrown() {
        int interval = MAX_CONCURRENT_REQUESTS * 500;

        int limit = MAX_CONCURRENT_REQUESTS;

        assertDoesNotThrow(() -> {
            Resilience4jRateLimit.fetch(client, TOTAL_REQUESTS, limit, interval)
                .doOnNext(System.out::println)
                .blockLast();
        });
    }

    @Test
    void givenShortLimit_whenUsingGuavaRateLimiter_thenNoExceptionThrown() {
        double limit = MAX_CONCURRENT_REQUESTS / 2;

        assertDoesNotThrow(() -> {
            GuavaRateLimit.fetch(client, TOTAL_REQUESTS, limit)
                .doOnNext(System.out::println)
                .blockLast();
        });
    }
}
