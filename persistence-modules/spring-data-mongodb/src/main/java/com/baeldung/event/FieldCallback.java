package com.baeldung.event;

import java.lang.reflect.Field;

import org.springframework.data.annotation.Id;
import org.springframework.util.ReflectionUtils;

public class FieldCallback implements ReflectionUtils.FieldCallback {
    private boolean idFound;

    @Override
    public void doWith(final Field field) throws IllegalArgumentException, IllegalAccessException {
        ReflectionUtils.makeAccessible(field);

        if (field.isAnnotationPresent(Id.class)) {
            idFound = true;
        }
    }

    public boolean isIdFound() {
        return idFound;
    }
}
