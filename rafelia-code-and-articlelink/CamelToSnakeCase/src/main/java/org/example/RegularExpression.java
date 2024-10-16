package org.example;

public class RegularExpression {

    public static String convertCamelToSnakeRegex(String camelCase) {
// Use regex to find uppercase letters and replace them with _ followed by lowercase letter
        String snakeCase = camelCase.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
        return snakeCase;
    }

    public static void main(String[] args) {
        String camelCase = "convertCamelCaseToSnakeCase";
        String snakeCase = convertCamelToSnakeRegex(camelCase);

        System.out.println("Camel Case: " + camelCase);
        System.out.println("Snake Case: " + snakeCase);
    }
}
