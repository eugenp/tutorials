package com.baeldung.concurrent.cyclicbarrier;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CyclicBarrierCompletionMethodExampleUnitTest {
    
    @Test
    public void whenCyclicBarrier_countTrips() {
        CyclicBarrierCompletionMethodExample ex = new CyclicBarrierCompletionMethodExample(7,20);
        int lineCount = ex.countTrips();
        assertEquals(2, lineCount);
    }
}
