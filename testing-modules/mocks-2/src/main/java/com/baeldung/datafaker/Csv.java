package com.baeldung.datafaker;

import net.datafaker.Faker;

public class Csv {
    private static final Faker faker = new Faker();

    public static void main(String[] args) {
        System.out.println("First expression:\n" + getFirstExpression());
        System.out.println("Second expression:\n" + getSecondExpression());
    }

    static String getSecondExpression() {
        final String secondExpressionString
            = "#{csv ',','\"','true','4','name_column','#{Name.first_name}','last_name_column','#{Name.last_name}'}";
        return faker.expression(secondExpressionString);
    }

    static String getFirstExpression() {
        final String firstExpressionString
            = "#{csv '4','name_column','#{Name.first_name}','last_name_column','#{Name.last_name}'}";
        return faker.expression(firstExpressionString);
    }

}
