package com.baeldung;

import org.junit.gen5.api.Disabled;
import org.junit.gen5.api.Test;

import static org.junit.gen5.api.Assertions.assertAll;
import static org.junit.gen5.api.Assertions.assertEquals;
import static org.junit.gen5.api.Assertions.assertTrue;

class FirstTest {

    @Test
    void lambdaExpressions() {
        String string = "";
        assertTrue(string::isEmpty, "String should be empty");
    }

    @Test
    void groupAssertions() {
        int[] numbers = {0,1,2,3,4};
        assertAll("numbers", () -> {
            assertEquals(numbers[0], 1);
            assertEquals(numbers[3], 3);
            assertEquals(numbers[4], 1);
        });
    }

    @Test
    @Disabled
    void disabledTest() {
        assertTrue(false);
    }
}
