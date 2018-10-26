package com.baeldung.concurrent.cyclicbarrier;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CyclicBarrierResetExampleUnitTest {
    
    @Test
    public void whenCyclicBarrier_reset() {
        CyclicBarrierResetExample ex = new CyclicBarrierResetExample(7,10);
        int lineCount = ex.countWaits();
        assertEquals(8, lineCount);
    }
}
