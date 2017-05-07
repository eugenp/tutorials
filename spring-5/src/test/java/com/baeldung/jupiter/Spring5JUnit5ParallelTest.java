package com.baeldung.jupiter;

import com.baeldung.Example1IntegrationTest;
import com.baeldung.Example2IntegrationTest;
import org.junit.experimental.ParallelComputer;
import org.junit.jupiter.api.Test;
import org.junit.runner.Computer;
import org.junit.runner.JUnitCore;

public class Spring5JUnit5ParallelTest {

    @Test
    public void givenTwoTestClasses_whenJUnitRunParallel_thenTheTestsExecutingParallel() {
        final Class<?>[] classes = {
          Example1IntegrationTest.class, Example2IntegrationTest.class
        };

        JUnitCore.runClasses(new ParallelComputer(true, true), classes);
    }

    @Test
    public void givenTwoTestClasses_whenJUnitRunParallel_thenTheTestsExecutingLinear() {
        final Class<?>[] classes = {
          Example1IntegrationTest.class, Example2IntegrationTest.class
        };

        JUnitCore.runClasses(new Computer(), classes);
    }
}