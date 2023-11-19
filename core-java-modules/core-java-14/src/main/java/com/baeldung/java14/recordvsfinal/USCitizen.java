package com.baeldung.java14.recordvsfinal;

public record USCitizen(String firstName, String lastName, String address) {
    static int countryCode;

    // static initializer
    static {
        countryCode = 1;
    }

    public static int getCountryCode() {
        return countryCode;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}