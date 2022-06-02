package com.baeldung.pow;

import PowerExample
import java.text.DecimalFormat;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPowerExample {

    @Test
    public static void testPowerExample1(String[] args) {

        assertEquals(8, PowerExample.intResult);

    }
    @Test
    public static void testPowerExample2(String[] args) {

        assertEquals(74, PowerExample.dblResult);

    }
    @Test
    public static void testPowerExample3(String[] args) {

        assertEquals(74.09, PowerExample.df);

    }
}