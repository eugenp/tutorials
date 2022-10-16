package com.baeldung.datafaker;

import net.datafaker.Faker;

import java.time.Duration;

public class MethodInvocationWithParams {
    public static final int MIN = 1;
    public static final int MAX = 10;
    public static final String UNIT = "SECONDS";
    private static final Faker faker = new Faker();

    public static void main(String[] args) {
        System.out.println("Duration from the method :" + getDurationFromMethod());
        System.out.println("Duration from the expression: " + getDurationFromExpression());
    }

    static String getDurationFromExpression() {
        return faker.expression("#{date.duration '1', '10', 'SECONDS'}");
    }

    static Duration getDurationFromMethod() {
        return faker.date().duration(MIN, MAX, UNIT);
    }
}
