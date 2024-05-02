package com.baeldung.passclassasparameter;

public class Example {
    public static void processClass(Class<?> clazz) {
        System.out.println("Processing class: " + clazz.getName());
    }

    public static void main(String[] args) {
        processClass(String.class);
        processClass(Integer.class);
        processClass(Double.class);
    }
}
