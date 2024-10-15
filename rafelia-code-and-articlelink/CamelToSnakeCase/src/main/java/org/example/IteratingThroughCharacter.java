package org.example;

public class IteratingThroughCharacter{

    public static String convertCamelToSnake(String camelCase) {
        StringBuilder snakeCase = new StringBuilder();

        for (int i = 0; i < camelCase.length(); i++) {
            char currentChar = camelCase.charAt(i);
// Check if character is uppercase
            if (Character.isUpperCase(currentChar)) {
// Append an underscore and convert it to lowercase
                snakeCase.append('_');
                snakeCase.append(Character.toLowerCase(currentChar));
            }
            else {
// Append the current character as it is
                snakeCase.append(currentChar);
            }
        }
        return snakeCase.toString();
    }

    public static void main(String[] args) {
        String camelCase = "camelCaseExample";
        String snakeCase = convertCamelToSnake(camelCase);

        System.out.println("Camel Case: " + camelCase);
        System.out.println("Snake Case: " + snakeCase);
    }
}
