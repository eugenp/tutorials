package com.baeldung.execution.time;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RandomNumberUnitTest {

    @Test
    public void whenTwoRandomNumberThenAssertUniqueness() {

        Assertions.assertNotEquals(Math.random(), Math.random());
    }
}
