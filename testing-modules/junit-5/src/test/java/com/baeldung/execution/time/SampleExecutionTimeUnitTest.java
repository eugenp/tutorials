package com.baeldung.execution.time;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SampleExecutionTimeUnitTest {

    @Test
    void someUnitTest() {

        assertTrue(doSomething());
    }

//    @Test
//    void someIntegrationTest() throws Exception {
//
//        //simulate an operation that may take 5 seconds
//        Thread.sleep(5000);
//
//        assertTrue(doSomething());
//    }
//
//    @Test
//    void someEndToEndTest() throws Exception {
//
//        //simulate an operation that may take 10 seconds
//        Thread.sleep(10000);
//
//        assertTrue(doSomething());
//
//    }

    private boolean doSomething() {
        return true;
    }
}
