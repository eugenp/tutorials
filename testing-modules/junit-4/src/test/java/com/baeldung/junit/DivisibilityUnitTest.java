package com.baeldung.junit;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class DivisibilityUnitTest {

    private static int number;

    @BeforeClass
    public static void setup() {
        number = 40;
    }

    @Test
    public void givenNumber_whenDivisibleByTwo_thenCorrect() {
        assertEquals(number % 2, 0);
    }
}
