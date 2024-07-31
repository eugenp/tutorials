package com.baeldung.hextorgb;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class HexToRgbUnitTest {

    @Test
    public void givenHexCode_whenConvertedToRgb_thenCorrectRgbValuesAreReturned() {
        String hexCode = "FF9933";
        int red = 255;
        int green = 153;
        int blue = 51;

        int resultRed = Integer.valueOf(hexCode.substring(0, 2), 16);
        int resultGreen = Integer.valueOf(hexCode.substring(2, 4), 16);
        int resultBlue = Integer.valueOf(hexCode.substring(4, 6), 16);

        assertEquals(red, resultRed);
        assertEquals(green, resultGreen);
        assertEquals(blue, resultBlue);
    }

}
