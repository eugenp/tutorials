package com.baeldung.helpervsutilityclasses;

public final class MyUtilityClass {

    private MyUtilityClass(){}

    public static String returnUpperCase(String stringInput) {
        return stringInput.toUpperCase();
    }

    public static String returnLowerCase(String stringInput) {
        return stringInput.toLowerCase();
    }

    public static String[] splitStringInput(String stringInput, String delimiter) {
        return stringInput.split(delimiter);
    }

}
