package com.baeldung.number_0xff;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class Number0xffUnitTest {

    @Test
    public void test0xFFAssignedToInteger() {
        int x = 0xff;
        int expectedValue = 255;
        assertEquals(expectedValue, x);
    }

    @Test
    public void test0xFFAssignedToByte() {
        byte y = (byte) 0xff;
        int expectedValue = -1;
        assertEquals(expectedValue, y);
    }

    @Test
    public void givenColor_whenGetRedColor_thenExtractRedColor() {
        int rgba = 272214023;
        int expectedValue = 16;
        assertEquals(expectedValue, Number0xff.getRedColor(rgba));
    }

    @Test
    public void givenColor_whenGetGreenColor_thenExtractGreenColor() {
        int rgba = 272214023;
        int expectedValue = 57;
        assertEquals(expectedValue, Number0xff.getGreenColor(rgba));
    }

    @Test
    public void givenColor_whenGetBlueColor_thenExtractBlueColor() {
        int rgba = 272214023;
        int expectedValue = 168;
        assertEquals(expectedValue, Number0xff.getBlueColor(rgba));
    }

    @Test
    public void givenColor_whenGetAlfa_thenExtractAlfa() {
        int rgba = 272214023;
        int expectedValue = 7;
        assertEquals(expectedValue, Number0xff.getAlfa(rgba));
    }
}
