package com.baeldung.execution.time;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class SampleExecutionTimeUnitTest {

    @Test
    public void someUnitTest() {

        assertTrue(this::doSomething);
    }

    @Test
    public void someIntegrationTest() {

        try {
            //simulate an operation that may take 5 seconds
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            fail();
        }

        assertTrue(this::doSomething);
    }

    @Test
    public void someEndToEndTest() {

        try {
            //simulate an operation that may take 10 seconds
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            fail();
        }

        assertTrue(this::doSomething);

    }

    private boolean doSomething() {
        return true;
    }
}
