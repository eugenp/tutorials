package com.baeldung.lombok.locked;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CounterUnitTest {

    @Test
    void givenCounter_whenIncrementCalledMultipleTimes_thenReturnCorrectResult() throws InterruptedException {
        Counter counter = new Counter();

        Thread.Builder builder = Thread.ofVirtual()
            .name("worker-", 0);
        Runnable task = counter::increment;

        Thread t1 = builder.start(task);
        t1.join();

        Thread t2 = builder.start(task);
        t2.join();

        assertEquals(2, counter.get());
    }
}
