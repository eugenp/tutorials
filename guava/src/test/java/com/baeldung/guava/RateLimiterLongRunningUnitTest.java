package com.baeldung.guava;


import com.google.common.util.concurrent.RateLimiter;
import org.junit.Test;

import java.time.ZonedDateTime;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class RateLimiterLongRunningUnitTest {

    @Test
    public void givenLimitedResource_whenUseRateLimiter_thenShouldLimitPermits() {
        //given
        RateLimiter rateLimiter = RateLimiter.create(100);

        //when
        long startTime = ZonedDateTime.now().getSecond();
        IntStream.range(0, 1000).forEach(i -> {
            rateLimiter.acquire();
            doSomeLimitedOperation();
        });
        long elapsedTimeSeconds = ZonedDateTime.now().getSecond() - startTime;

        //then
        assertThat(elapsedTimeSeconds >= 10);
    }

    @Test
    public void givenLimitedResource_whenRequestTwice_thenShouldPermitWithoutBlocking() {
        //given
        RateLimiter rateLimiter = RateLimiter.create(2);

        //when
        long startTime = ZonedDateTime.now().getSecond();
        rateLimiter.acquire(1);
        doSomeLimitedOperation();
        rateLimiter.acquire(1);
        doSomeLimitedOperation();
        long elapsedTimeSeconds = ZonedDateTime.now().getSecond() - startTime;

        //then
        assertThat(elapsedTimeSeconds <= 1);
    }

    @Test
    public void givenLimitedResource_whenRequestOnce_thenShouldPermitWithoutBlocking() {
        //given
        RateLimiter rateLimiter = RateLimiter.create(100);

        //when
        long startTime = ZonedDateTime.now().getSecond();
        rateLimiter.acquire(100);
        doSomeLimitedOperation();
        long elapsedTimeSeconds = ZonedDateTime.now().getSecond() - startTime;

        //then
        assertThat(elapsedTimeSeconds <= 1);
    }

    @Test
    public void givenLimitedResource_whenTryAcquire_shouldNotBlockIndefinitely() {
        //given
        RateLimiter rateLimiter = RateLimiter.create(1);

        //when
        rateLimiter.acquire();
        boolean result = rateLimiter.tryAcquire(2, 10, TimeUnit.MILLISECONDS);

        //then
        assertThat(result).isFalse();

    }

    private void doSomeLimitedOperation() {
        //some computing
    }

}
