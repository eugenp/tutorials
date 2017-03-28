package com.baeldung;

import org.junit.Test;
import org.junit.experimental.ParallelComputer;
import org.junit.runner.Computer;
import org.junit.runner.JUnitCore;

public class ParallelExampleIntegrationTest {

    @Test
    public void runTests() {
        final Class<?>[] classes = { ExampleIntegrationTest.class, Example2IntegrationTest.class };

        JUnitCore.runClasses(new Computer(), classes);
    }

    @Test
    public void runTestsInParallel() {
        final Class<?>[] classes = { ExampleIntegrationTest.class, Example2IntegrationTest.class };

        JUnitCore.runClasses(new ParallelComputer(true, true), classes);
    }

}