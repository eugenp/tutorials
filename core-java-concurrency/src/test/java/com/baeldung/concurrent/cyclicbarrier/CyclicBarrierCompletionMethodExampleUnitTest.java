package com.baeldung.concurrent.cyclicbarrier;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

public class CyclicBarrierCompletionMethodExampleUnitTest {
    
    @Test
    public void whenCyclicBarrier_countTrips() {
        CyclicBarrierCompletionMethodExample ex = new CyclicBarrierCompletionMethodExample(new ArrayList<>(),5,20);
        int lineCount = ex.countTrips();
        assertEquals(4, lineCount);
    }
}
