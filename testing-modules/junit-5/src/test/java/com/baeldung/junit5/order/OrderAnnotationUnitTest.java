package com.baeldung.junit5.order;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(OrderAnnotation.class)
public class OrderAnnotationUnitTest {

    private static final StringBuilder output = new StringBuilder();

    @Test
    @Order(1)
    void firstTest() {
        output.append("a");
    }

    @Test
    @Order(2)
    void secondTest() {
        output.append("b");
    }

    @Test
    @Order(3)
    void thirdTest() {
        output.append("c");
    }

    @AfterAll
    public static void assertOutput() {
        assertEquals("abc", output.toString());
    }
}
