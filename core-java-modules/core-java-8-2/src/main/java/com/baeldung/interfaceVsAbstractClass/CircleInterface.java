package com.baeldung.interfaceVsAbstractClass;

import java.util.Arrays;
import java.util.List;

public interface CircleInterface {
    List<String> allowedColors = Arrays.asList("RED", "GREEN", "BLUE");

    String getColor();
    
    public default boolean isValid() {
        if (allowedColors.contains(getColor())) {
            return true;
        } else {
            return false;
        }
    }
}
