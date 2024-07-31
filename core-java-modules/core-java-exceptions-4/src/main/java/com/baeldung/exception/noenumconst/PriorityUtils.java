package com.baeldung.exception.noenumconst;

public class PriorityUtils {

    public static Priority getByName(String name) {
        return Priority.valueOf(name);
    }

    public static Priority getByUpperCaseName(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }

        return Priority.valueOf(name.toUpperCase());
    }

    public static void main(String[] args) {
        System.out.println(getByName("Low"));
    }

}
