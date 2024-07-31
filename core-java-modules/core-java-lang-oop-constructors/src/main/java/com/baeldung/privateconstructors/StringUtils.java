package com.baeldung.privateconstructors;

public class StringUtils {

    private StringUtils() {
        // this class cannot be instantiated
    }

    public static String toUpperCase(String s) {
        return s.toUpperCase();
    }

    public static String toLowerCase(String s) {
        return s.toLowerCase();
    }
}
