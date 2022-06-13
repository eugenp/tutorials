package com.baeldung;

import org.junit.Test;
import org.junit.experimental.ParallelComputer;
import org.junit.runner.Computer;
import org.junit.runner.JUnitCore;

public class ParallelManualTest {

    @Test
    public void runTests() {
        final Class<?>[] classes = { Example1ManualTest.class, Example2ManualTest.class };

        JUnitCore.runClasses(new Computer(), classes);
    }

    @Test
    public void runTestsInParallel() {
        final Class<?>[] classes = { Example1ManualTest.class, Example2ManualTest.class };

        JUnitCore.runClasses(new ParallelComputer(true, true), classes);
    }

}