package com.baeldung.interfaceVsAbstractClass;

import java.util.Arrays;
import java.util.List;

public interface CircleInterface {
    List<String> allowedColors = Arrays.asList("RED", "GREEN", "BLUE");

    public default boolean isValid(String color) {
        if (allowedColors.contains(color)) {
            return true;
        } else {
            return false;
        }
    }
}
