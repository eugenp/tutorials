package com.baeldung.privateconstructors;

public class StringUtils {

    private StringUtils() {
        System.out.println("This class cannot be instantiated");
    }

    public static String toUpperCase(String s) {
        return s.toUpperCase();
    }

    public static String toLowerCase(String s) {
        return s.toLowerCase();
    }
}
