package com.example;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class CalculatorJUnit5Test {

    @Tag("fast")
    @Test
    public void testAdd() {
        assertEquals(42, Integer.sum(19, 23));
    }

    @Tag("slow")
    @Test
    public void testAddMaxInteger() {
        assertEquals(2147483646, Integer.sum(2147183646, 300000));
    }

    @Tag("fast")
    @Test
    public void testAddZero() {
        assertEquals(21, Integer.sum(21, 0));
    }

    
    @Test
    public void testCalculate() {
        Integer number = Integer.valueOf(5);
        assertEquals(Integer.valueOf(8), number.add(3));
    }
}
