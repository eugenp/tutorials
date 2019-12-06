package com.baeldung.concurrent.countdownlatch;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CountdownLatchCountExampleUnitTest {

    @Test
    public void whenCountDownLatch_completed() {
        CountdownLatchCountExample ex = new CountdownLatchCountExample(2);
        boolean isCompleted = ex.callTwiceInSameThread();
        assertTrue(isCompleted);
    }
}
