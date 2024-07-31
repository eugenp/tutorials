package com.baeldung.datafaker;

import net.datafaker.Faker;

public class Regexify {
    private static final Faker faker = new Faker();

    public static void main(String[] args) {
        System.out.println("Expression: " + getExpression());
        System.out.println("Regexify with a method: " + getMethodExpression());
    }

    static String getMethodExpression() {
        return faker.regexify("[A-D]{4,10}");
    }

    static String getExpression() {
        return faker.expression("#{regexify '(hello|bye|hey)'}");
    }
}
