package com.baeldung.concurrent.countdownlatch;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CountdownLatchResetExampleManualTest {
    
    @Test
    public void whenCountDownLatch_noReset() {
        CountdownLatchResetExample ex = new CountdownLatchResetExample(7,20);
        int lineCount = ex.countWaits();
        assertTrue(lineCount <= 7);
    }
}
