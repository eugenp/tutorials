package com.baeldung.exceptions.nosuchfielderror;

public class FieldErrorExample {

    public static void main(String... args) {

        fetchAndPrint();
    }

    public static String getDependentMessage() {

        return Dependency.message;
    }

    public static void fetchAndPrint() {

        System.out.println(getDependentMessage());
    }

}