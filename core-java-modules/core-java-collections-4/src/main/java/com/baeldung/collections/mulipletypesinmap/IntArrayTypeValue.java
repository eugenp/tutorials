package com.baeldung.collections.mulipletypesinmap;

import java.util.Arrays;

public class IntArrayTypeValue implements DynamicTypeValue {
    private int[] value;

    public IntArrayTypeValue(int[] value) {
        this.value = value;
    }

    @Override
    public String valueDescription() {
        if (value == null) {
            return "The value is null.";
        }
        return String.format("The value is an array of %d integers: %s", value.length, Arrays.toString(value));
    }

}
