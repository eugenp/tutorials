package com.baeldung.java14.recordvslombok;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;

@Value
@Getter(AccessLevel.NONE)
public class ColorValueObject {
    int red;
    int green;
    int blue;

    public String getHexString() {
        return String.format("#%02X%02X%02X", red, green, blue);
    }
}
