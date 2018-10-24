package com.baeldung.junit5.runfromjava;

import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SecondUnitTest {

    @RepeatedTest(10)
    void whenSomething_thenSomething() {
        assertTrue(true);
    }

    @RepeatedTest(5)
    void whenSomethingElse_thenSomethingElse() {
        assertTrue(true);
    }
}
