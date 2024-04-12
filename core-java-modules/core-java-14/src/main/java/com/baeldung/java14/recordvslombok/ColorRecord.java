package com.baeldung.java14.recordvslombok;

public record ColorRecord(int red, int green, int blue) {

    public String getHexString() {
        return String.format("#%02X%02X%02X", red, green, blue);
    }
}
