package com.baeldung.concurrent.cyclicbarrier;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class CyclicBarrierCountExampleUnitTest {

    @Test
    public void whenCyclicBarrier_notCompleted() {
        CyclicBarrierCountExample ex = new CyclicBarrierCountExample(2);
        boolean isCompleted = ex.callTwiceInSameThread();
        assertFalse(isCompleted);
    }
}
