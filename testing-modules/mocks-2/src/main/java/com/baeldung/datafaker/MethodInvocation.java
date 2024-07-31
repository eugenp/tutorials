package com.baeldung.datafaker;

import net.datafaker.Faker;

public class MethodInvocation {
    private static final Faker faker = new Faker();

    public static void main(String[] args) {
        System.out.println("Name from a method: " + getNameFromMethod());
        System.out.println("Name from an expression: " + getNameFromExpression());
    }

    static String getNameFromExpression() {
        return faker.expression("#{Name.first_Name}");
    }

    static String getNameFromMethod() {
        return faker.name().firstName();
    }
}
