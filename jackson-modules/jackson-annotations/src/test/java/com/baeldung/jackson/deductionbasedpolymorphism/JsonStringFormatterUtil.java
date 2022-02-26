package com.baeldung.jackson.deductionbasedpolymorphism;

public class JsonStringFormatterUtil {

    public static String formatJson(String input) {
        return input.replaceAll("'", "\"");
    }

}
