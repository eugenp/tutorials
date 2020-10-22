package com.baeldung.number_0xff;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class Number0xffUtitTest {

    @Test
    public void test0xFFAssignedToInteger() {
        int x = 0xff;
        int expectedValue = 255;
        assertEquals(x, expectedValue);
    }

    @Test
    public void test0xFFAssignedToByte() {
        byte y = (byte) 0xff;
        int expectedValue = -1;
        assertEquals(x, expectedValue);
    }

    @Test
    public void givenColor_thenExtractRedColor() {
        int rgba = 272214023;
        int expectedValue = 64;
        assertEquals(Number0xff.getRedColor(rgba), expectedValue);
    }

    @Test
    public void givenColor_thenExtractGreenColor() {
        int rgba = 272214023;
        int expectedValue = 57;
        assertEquals(Number0xff.getGreenColor(rgba), expectedValue);
    }

    @Test
    public void givenColor_thenExtractBlueColor() {
        int rgba = 272214023;
        int expectedValue = 168;
        assertEquals(Number0xff.getBlueColor(rgba), expectedValue);
    }

    @Test
    public void givenColor_thenExtractAlfa() {
        int rgba = 272214023;
        int expectedValue = 7;
        assertEquals(Number0xff.getAlfa(rgba), expectedValue);
    }
}
