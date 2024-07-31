package com.baeldung.datafaker;

import net.datafaker.Faker;

public class Option {
    private static final Faker faker = new Faker();

    public static void main(String[] args) {
        System.out.println("First expression: " + getFirstExpression());
        System.out.println("Second expression: " + getSecondExpression());
        System.out.println("Third expression: " + getThirdExpression());
    }

    static String getThirdExpression() {
        return faker.expression("#{regexify '(Hi|Hello|Hey)'}");
    }

    static String getSecondExpression() {
        return faker.expression("#{options.option '1','2','3','4','*'}");
    }

    static String getFirstExpression() {
        return faker.expression("#{options.option 'Hi','Hello','Hey'}");
    }
}
