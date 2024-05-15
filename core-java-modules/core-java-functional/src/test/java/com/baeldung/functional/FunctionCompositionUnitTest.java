package com.baeldung.functional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class FunctionCompositionUnitTest {

    @Test
    public void testLogThenSqrt() {

        assertEquals(1.07, FunctionComposition.logThenSqrt(3.14), 0.01);

    }

    @Test
    public void testSqrtThenLog() {

        assertEquals(0.57, FunctionComposition.sqrtThenLog(3.14), 0.01);

    }

}
