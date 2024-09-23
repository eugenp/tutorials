package com.baeldung.mockito.utils;

public class ClassWithFinalField {
    public final String finalField = "Original Value";

    public String getFinalField() {
        return finalField;
    }
}
