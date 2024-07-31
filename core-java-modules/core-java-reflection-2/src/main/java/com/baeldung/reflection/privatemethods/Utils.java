package com.baeldung.reflection.privatemethods;

public class Utils {

    public static Integer validateAndDouble(Integer input) {
        if (input == null) {
            throw new IllegalArgumentException("input should not be null");
        }
        return doubleInteger(input);
    }

    private static Integer doubleInteger(Integer input) {
        if (input == null) {
            return null;
        }
        return 2 * input;
    }

}
