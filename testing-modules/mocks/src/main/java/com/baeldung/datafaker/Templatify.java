package com.baeldung.datafaker;

import net.datafaker.Faker;

public class Templatify {
    private static final Faker faker = new Faker();

    public static void main(String[] args) {
        System.out.println("Expression: " + getExpression());
        System.out.println("Expression with a placeholder: " + getExpressionWithPlaceholder());
    }

    static String getExpressionWithPlaceholder() {
        return faker.expression("#{templatify '#ight', '#', 'f', 'l', 'm', 'n'}");
    }

    static String getExpression() {
        return faker.expression("#{templatify 'test','t','j','r'}");
    }
}

