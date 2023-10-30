package com.baeldung.streams;

import java.util.Arrays;
import java.util.List;

public class Detail {

    private static final List<String> PARTS = Arrays.asList("turbine", "pump");

    public List<String> getParts() {
        return PARTS;
    }
}
