package com.baeldung.concurrent.cyclicbarrier;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

public class CyclicBarrierResetExampleUnitTest {
    
    @Test
    public void whenCyclicBarrier_reset() {
        CyclicBarrierResetExample ex = new CyclicBarrierResetExample(new ArrayList<>(),5,20);
        int lineCount = ex.countWaits();
        assertEquals(16, lineCount);
    }
}
