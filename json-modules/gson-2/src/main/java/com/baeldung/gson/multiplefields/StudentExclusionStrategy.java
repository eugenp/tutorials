package com.baeldung.gson.multiplefields;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class StudentExclusionStrategy implements ExclusionStrategy {

    @Override
    public boolean shouldSkipField(FieldAttributes field) {
        return field.getDeclaringClass() == LegacyStudent.class;
    }

    @Override
    public boolean shouldSkipClass(Class<?> aClass) {
        return false;
    }
}
