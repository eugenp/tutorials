package com.baeldung.rgb;

import com.baeldung.rbg.Rgb;
import com.baeldung.rbg.RgbConversions;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class RgbConversionUnitTest {

    @Test
    public void whenBasicPackingAndUnpackingRgb_thenReturnInitialComponentsValues() {
        int alpha = 255;
        int red = 255;
        int green = 0;
        int blue = 0;

        int rgb = (alpha << 24) | (red << 16) | (green << 8) | blue;

        assertEquals(rgb, 0xFFFF0000);

        int alphaReconstructed = (rgb >> 24) & 0xFF;
        int redReconstructed = (rgb >> 16) & 0xFF;
        int greenReconstructed = (rgb >> 8) & 0xFF;
        int blueReconstructed = rgb & 0xFF;

        assertEquals(alphaReconstructed, alpha);
        assertEquals(redReconstructed, red);
        assertEquals(greenReconstructed, green);
        assertEquals(blueReconstructed, blue);
    }

    @Test
    public void whenRgbaToIntWithoutClamping_ReturnIntegerRepresentations() {
        // Fully opaque red
        assertEquals(0xFFFF0000, RgbConversions.rgbToInt(255, 255, 0, 0));
        // Fully transparent blue
        assertEquals(0x000000FF, RgbConversions.rgbToInt(0, 0, 0, 255));
        // 50% transparent green
        assertEquals(0x8000FF00, RgbConversions.rgbToInt(128, 0, 255, 0));
    }

    @Test
    public void whenRgbaToIntWithClamping_ReturnIntegerRepresentations() {
        // Clamping for values below 0
        assertEquals(0x00000000, RgbConversions.rgbToInt(-1, -1, -1, -1));
        // Clamping for values above 255
        assertEquals(0xFFFFFFFF, RgbConversions.rgbToInt(256, 256, 256, 256));
    }

    @Test
    public void whenRgbaToIntBoundary_ReturnIntegerRepresentations() {
        // Boundary values for each color component including alpha
        assertEquals(0xFF000000, RgbConversions.rgbToInt(255, 0, 0, 0)); // Opaque black
        assertEquals(0x00FFFFFF, RgbConversions.rgbToInt(0, 255, 255, 255)); // Transparent white
    }

    @Test
    public void whenRgbaToIntAndIntToRgb_ReturnTheSameValues() {
        Rgb rgb = new Rgb(0, 125, 125, 100);
        assertEquals(rgb, RgbConversions.intToRgb(RgbConversions.rgbToInt(rgb)));
    }

    @Test
    public void whenBrightnessAdjustment_ReturnChangedRgbIntegerValue() {
        float scale = 0.8f; // darken by 20%

        // initial values
        int alpha = 0;
        int red = 100;
        int green = 255;
        int blue = 100;


        int adjustedRed = (int)(red * scale);
        int adjustedGreen = (int)(green * scale);
        int adjustedBlue = (int)(blue * scale);

        int newArgb = (alpha << 24) | (adjustedRed << 16) | (adjustedGreen << 8) | adjustedBlue;

        assertEquals(newArgb, 0x50CC50);
    }

    @Test
    public void whenGrayscaleConversion_ReturnChangedRgbIntegerValue() {
        float scale = 0.8f; // darken by 20%

        // initial values
        int alpha = 0;
        int red = 100;
        int green = 255;
        int blue = 100;

        int average = (int)(red * 0.299 + green * 0.587 + blue * 0.114);
        int grayscaleArgb = (alpha << 24) | (average << 16) | (average << 8) | average;

        assertEquals(grayscaleArgb, 0xBEBEBE);
    }

    @Test
    public void whenInversion_ReturnChangedRgbIntegerValue() {
        float scale = 0.8f; // darken by 20%

        // initial values
        int alpha = 0;
        int red = 100;
        int green = 255;
        int blue = 100;

        red = 255 - red;
        green = 255 - green;
        blue = 255 - blue;

        int invertedArgb = (alpha << 24) | (red << 16) | (green << 8) | blue;

        assertEquals(invertedArgb, 0x9B009B);
    }
}
