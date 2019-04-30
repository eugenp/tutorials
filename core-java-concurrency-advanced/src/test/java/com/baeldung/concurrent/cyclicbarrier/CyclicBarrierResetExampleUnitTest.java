package com.baeldung.concurrent.cyclicbarrier;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CyclicBarrierResetExampleUnitTest {
    
    @Test
    public void whenCyclicBarrier_reset() {
        CyclicBarrierResetExample ex = new CyclicBarrierResetExample(7,20);
        int lineCount = ex.countWaits();
        assertTrue(lineCount > 7);
    }
}
