package com.baeldung.gson.multiplefields;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class ImprovedStudentExclusionStrategy implements ExclusionStrategy {

    @Override
    public boolean shouldSkipField(FieldAttributes field) {
        if (field.getDeclaringClass() == StudentV2.class) {
            return !field.getName()
                .equals("major");
        }
        return field.getDeclaringClass() == StudentV1.class;
    }

    @Override
    public boolean shouldSkipClass(Class<?> aClass) {
        return false;
    }
}
