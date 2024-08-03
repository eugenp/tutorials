package com.baeldung.junit.norunnablemethods;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JUnit5UnitTest {

    private static int expected = 1;

    @BeforeAll
    public static void initialize() {
        System.out.println("Intializes test setup");
    }

    @AfterAll
    public static void tearDown() {
        System.out.println("Test clean-up");
    }

    @BeforeEach
    public void initTest() {
        expected = 2;
    }

    @AfterEach
    public void cleanUp() {
        expected = 0;
    }

    @Test
    public void givenSimpleTest_whenExecuted_thenReturnExpected() {
        assertEquals(expected, 2);
    }

    @Test
    public void givenAnotherSimpleTest_whenExecuted_thenReturnExpected() {
        assertEquals(expected, 2);
    }

}
