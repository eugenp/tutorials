package com.baeldung.gson.multiplefields;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class StudentExclusionStrategy implements ExclusionStrategy {

    @Override
    public boolean shouldSkipField(FieldAttributes field) {
        // Ignore all field values from V1 type
        return field.getDeclaringClass() == StudentV1.class;
    }

    @Override
    public boolean shouldSkipClass(Class<?> aClass) {
        return false;
    }
}
