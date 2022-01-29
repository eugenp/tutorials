package com.baeldung.jackson.deduction_based_polymorphism;

public class JsonStringFormatterUtil {

    public static String formatJson(String input) {
        return input.replaceAll("'", "\"");
    }

}
