package com.baeldung;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class AssertionUnitTest {

    @Test
    public void testConvertToDoubleThrowException() {
        String age = "eighteen";
        assertThrows(NumberFormatException.class, () -> {
            convertToInt(age);
        });

        assertThrows(NumberFormatException.class, () -> {
            convertToInt(age);
        });
    }

    private static Integer convertToInt(String str) {
        if (str == null) {
            return null;
        }
        return Integer.valueOf(str);
    }

}
