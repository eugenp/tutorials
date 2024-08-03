package com.baeldung.junit.norunnablemethods;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class JUnit4UnitTest {

    private static int expected = 1;

    @BeforeClass
    public static void initialize() {
        System.out.println("Intializes test setup");
    }

    @AfterClass
    public static void tearDown() {
        System.out.println("Test clean-up");
    }

    @Before
    public void initTest() {
        expected = 2;
    }

    @After
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
