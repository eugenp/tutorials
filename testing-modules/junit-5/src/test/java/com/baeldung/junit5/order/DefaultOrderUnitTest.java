package com.baeldung.junit5.order;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DefaultOrderUnitTest {

    private static StringBuilder output = new StringBuilder("");

    @Test
    @DisplayName("Test A")
    public void myATest() {
        output.append("A");
    }

    @Test
    @DisplayName("Test B")
    public void myBTest() {
        output.append("B");
    }

    @Test
    @DisplayName("Test C")
    public void myCTest() {
        output.append("C");
    }

    @AfterAll
    public static void assertOutput() {
        assertEquals(output.toString(), "ABC");
    }

}
