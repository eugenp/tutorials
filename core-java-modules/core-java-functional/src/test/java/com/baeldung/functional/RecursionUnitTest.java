package com.baeldung.functional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class RecursionUnitTest {

    @Test
    public void testHeadRecursion() {

        assertEquals(120, Recursion.headRecursion(5));

    }

    @Test
    public void testTailRecursion() {

        assertEquals(120, Recursion.tailRecursion(5, 1));

    }

}
