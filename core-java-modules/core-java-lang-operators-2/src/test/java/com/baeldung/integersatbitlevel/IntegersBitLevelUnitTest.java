package com.baeldung.integersatbitlevel;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

class IntegersBitLevelUnitTest {

    @Test
    void givenNumbers_whenBitwiseAND_thenResultIsExpected() {
        int result = 0b1100 & 0b0111;
        assertEquals(0b0100, result);
    }

    @Test
    void givenNumbers_whenBitwiseOR_thenResultIsExpected() {
        int result = 0b1100 | 0b0111;
        assertEquals(0b1111, result);
    }

    @Test
    void givenNumbers_whenBitwiseXOR_thenResultIsExpected() {
        int result = 0b1100 ^ 0b0111;
        assertEquals(0b1011, result);
    }

    @Test
    void givenNumber_whenBitwiseNOT_thenResultIsExpected() {
        int result = ~0b0101;
        assertEquals(-0b0110, result);
    }

    @Test
    void givenNumber_whenBitwiseLeftShift_thenResultIsExpected() {
        int result = 0b0101 << 2;
        assertEquals(0b10100, result);
    }

    @Test
    void givenNumber_whenBitwiseRightShift_thenResultIsExpected() {
        int result = 0b0101 >> 1;
        assertEquals(0b10, result);
    }

    @Test
    void givenOriginalColor_whenApplyingMask_thenObtainModifiedColor() {
        int originalColor = 0xFF336699;

        int alphaMask = 0xFF000000;
        int redMask = 0x00FF0000;
        int greenMask = 0x0000FF00;
        int blueMask = 0x000000FF;

        int alpha = (originalColor & alphaMask) >>> 24;
        int red = (originalColor & redMask) >>> 16;
        int green = (originalColor & greenMask) >>> 8;
        int blue = originalColor & blueMask;

        red = Math.min(255, red + 50);
        green = Math.min(255, green + 30);

        int modifiedColor = (alpha << 24) | (red << 16) | (green << 8) | blue;

        assertEquals(-10124135, modifiedColor);
    }
}
