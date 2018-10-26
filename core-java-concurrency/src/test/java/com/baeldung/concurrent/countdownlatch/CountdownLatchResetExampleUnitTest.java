package com.baeldung.concurrent.countdownlatch;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

public class CountdownLatchResetExampleUnitTest {
    
    @Test
    public void whenCountDownLatch_noReset() {
        CountdownLatchResetExample ex = new CountdownLatchResetExample(5,20);
        int lineCount = ex.countWaits();
        assertEquals(5, lineCount);
    }
}
