package com.baeldung.junit5.order;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(CustomOrder.class)
public class CustomOrderUnitTest {

    private static final StringBuilder output = new StringBuilder("");

    @Test
    void myATest() {
        output.append("A");
    }

    @Test
    void myBTest() {
        output.append("B");
    }

    @Test
    void myaTest() {
        output.append("a");
    }

    @AfterAll
    public static void assertOutput() {
        assertEquals("AaB", output.toString());
    }
}
