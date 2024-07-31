package com.baeldung.java14.recordvslombok;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ColorData {

    private int red;
    private int green;
    private int blue;

    public String getHexString() {
        return String.format("#%02X%02X%02X", red, green, blue);
    }

}
