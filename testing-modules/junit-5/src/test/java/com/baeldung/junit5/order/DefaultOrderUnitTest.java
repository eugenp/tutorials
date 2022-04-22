package com.baeldung.junit5.order;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DefaultOrderUnitTest {

    private static StringBuilder output = new StringBuilder("");

    @Test
    @DisplayName("Test A")
    void myATest() {
        output.append("A");
    }

    @Test
    @DisplayName("Test B")
    void myBTest() {
        output.append("B");
    }

    @Test
    @DisplayName("Test C")
    void myCTest() {
        output.append("C");
    }

    @AfterAll
    public static void assertOutput() {
        assertEquals(output.toString(), "ABC");
    }

}
