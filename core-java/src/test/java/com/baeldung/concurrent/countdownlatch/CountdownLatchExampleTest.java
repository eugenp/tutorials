package com.baeldung.concurrent.countdownlatch;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class CountdownLatchExampleTest {
    @Test
    public void whenParallelProcessing_thenMainThreadWillBlockUntilCompletion() throws InterruptedException {

        // Given
        List<String> outputScraper = Collections.synchronizedList(new ArrayList<>());
        CountDownLatch countDownLatch = new CountDownLatch(5);
        List<Thread> workers = Stream
          .generate(() -> new Thread(new Worker(outputScraper, countDownLatch)))
          .limit(5)
          .collect(toList());

        // When
        workers.forEach(Thread::start);
        countDownLatch.await(); // Block until workers finish
        outputScraper.add("Latch released");

        // Then
        outputScraper.forEach(Object::toString);
        assertThat(outputScraper)
          .containsExactly(
            "Counted down",
            "Counted down",
            "Counted down",
            "Counted down",
            "Counted down",
            "Latch released"
          );
    }

    @Test
    public void whenFailingToParallelProcess_thenMainThreadShouldTimeout() throws InterruptedException {
        // Given
        List<String> outputScraper = Collections.synchronizedList(new ArrayList<>());
        CountDownLatch countDownLatch = new CountDownLatch(5);
        List<Thread> workers = Stream
          .generate(() -> new Thread(new BrokenWorker(outputScraper, countDownLatch)))
          .limit(5)
          .collect(toList());

        // When
        workers.forEach(Thread::start);
        final boolean result = countDownLatch.await(3L, TimeUnit.SECONDS);

        // Then
        assertThat(result).isTrue();
    }
}