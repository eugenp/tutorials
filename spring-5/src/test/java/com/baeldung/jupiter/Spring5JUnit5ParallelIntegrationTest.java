package com.baeldung.jupiter;

import com.baeldung.Example1IntegrationTest;
import com.baeldung.Example2IntegrationTest;
import org.junit.experimental.ParallelComputer;
import org.junit.jupiter.api.Test;
import org.junit.runner.Computer;
import org.junit.runner.JUnitCore;

class Spring5JUnit5ParallelIntegrationTest {

    @Test
    void givenTwoTestClasses_whenJUnitRunParallel_thenTheTestsExecutingParallel() {
        final Class<?>[] classes = { Example1IntegrationTest.class, Example2IntegrationTest.class };

        JUnitCore.runClasses(new ParallelComputer(true, true), classes);
    }

    @Test
    void givenTwoTestClasses_whenJUnitRunParallel_thenTheTestsExecutingLinear() {
        final Class<?>[] classes = { Example1IntegrationTest.class, Example2IntegrationTest.class };

        JUnitCore.runClasses(new Computer(), classes);
    }
}