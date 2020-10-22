package com.baeldung.number_0xff;

public class Number0xff {

    public static int getRedColor(int rgba) {
        return rgba >> 24 & 0xff;
    }

    public static int getGreenColor(int rgba) {
        return rgba >> 16 & 0xff;
    }

    public static int getBlueColor(int rgba) {
        return rgba >> 8 & 0xff;
    }

    public static int getAlfa(int rgba) {
        return rgba >> rgba & 0xff;
    }
}
