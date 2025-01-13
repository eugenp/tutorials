package com.baeldung.cameltosnakecase;

public class CamelToSnakeCaseConverter {

    public static String convertCamelCaseToSnake(String input) {
        StringBuilder result = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (Character.isUpperCase(c)) {
                result.append("_").append(Character.toLowerCase(c));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    public static String convertCamelCaseToSnakeRegex(String input) {
        return input
            .replaceAll("([A-Z])(?=[A-Z])", "$1_")
            .replaceAll("([a-z])([A-Z])", "$1_$2")
            .toLowerCase();
    }
}