package com.baeldung.patterns.hexagonal.domain;

public class GreekNameGenerator implements NameGenerator {

    public String getName(int birthMonth) {
        if (birthMonth < 1 || birthMonth > 12) {
            return null;
        }
        if (birthMonth < 6) {
            return "Athena";
        }
        return "Aphrodite";
    }

}
