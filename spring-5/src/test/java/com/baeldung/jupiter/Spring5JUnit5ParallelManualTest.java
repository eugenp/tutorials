package com.baeldung.jupiter;

import com.baeldung.Example1ManualTest;
import com.baeldung.Example2ManualTest;
import org.junit.experimental.ParallelComputer;
import org.junit.jupiter.api.Test;
import org.junit.runner.Computer;
import org.junit.runner.JUnitCore;

class Spring5JUnit5ParallelManualTest {

    @Test
    void givenTwoTestClasses_whenJUnitRunParallel_thenTheTestsExecutingParallel() {
        final Class<?>[] classes = { Example1ManualTest.class, Example2ManualTest.class };

        JUnitCore.runClasses(new ParallelComputer(true, true), classes);
    }

    @Test
    void givenTwoTestClasses_whenJUnitRunParallel_thenTheTestsExecutingLinear() {
        final Class<?>[] classes = { Example1ManualTest.class, Example2ManualTest.class };

        JUnitCore.runClasses(new Computer(), classes);
    }
}