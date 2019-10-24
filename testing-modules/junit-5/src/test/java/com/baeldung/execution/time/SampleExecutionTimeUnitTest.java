package com.baeldung.execution.time;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class SampleExecutionTimeUnitTest {

    @Test
    public void someUnitTest() {

        assertTrue(SampleTest::doSomething);
    }

}
