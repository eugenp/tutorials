package com.baeldung.rbg;

public class RgbConversions {

    public static int rgbToInt(int alpha, int red, int green, int blue) {
        alpha = clamp(alpha, 0, 255);
        red = clamp(red, 0, 255);
        green = clamp(green, 0, 255);
        blue = clamp(blue, 0, 255);

        return (alpha << 24) | (red << 16) | (green << 8) | blue;
    }

    public static int rgbToInt(Rgb rgb) {
        return rgbToInt(rgb.getAlpha(), rgb.getRed(), rgb.getGreen(), rgb.getBlue());
    }

    public static Rgb intToRgb(int argb) {
        int alpha = (argb >> 24) & 0xFF;
        int red = (argb >> 16) & 0xFF;
        int green = (argb >> 8) & 0xFF;
        int blue = argb & 0xFF;

        return new Rgb(alpha, red, green, blue);
    }

    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
}

