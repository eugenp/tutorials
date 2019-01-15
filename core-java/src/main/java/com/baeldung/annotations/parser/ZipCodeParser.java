package com.baeldung.annotations.parser;

import com.baeldung.annotations.ZipCode;

import java.lang.reflect.Field;

public class ZipCodeParser {

    /**
     * @param object is validated to check @ZipCode fields are properly populated
     * @throws IllegalArgumentException when @ZipCode fields do not store numeric values or have more than 6 digits
     */
    public static <T> void validate(T object) throws IllegalArgumentException {
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            if (field.isAnnotationPresent(ZipCode.class)) {
                try {
                    Object fieldValue = field.get(object);
                    if (fieldValue != null) {
                        try {
                            int value = Integer.parseInt(fieldValue.toString());
                            if (value < 100000 || value > 999999) {
                                throw new IllegalArgumentException("@ZipCode field must be 6 digits");
                            }
                        } catch (NumberFormatException e) {
                            throw new IllegalArgumentException("@ZipCode field must be numeric");
                        }
                    }
                } catch (IllegalAccessException ignored) {
                }
            }
        }
    }
}
