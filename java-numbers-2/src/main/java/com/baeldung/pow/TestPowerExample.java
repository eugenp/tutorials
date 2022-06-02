package com.baeldung.pow;

import PowerExample
import java.text.DecimalFormat;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPowerExample {

    @Test
    public static void main(String[] args) {

        assertEquals(8, PowerExample.intResult);

        assertEquals(74, PowerExample.dblResult);

        assertEquals(74.09, PowerExample.df);

    }
}