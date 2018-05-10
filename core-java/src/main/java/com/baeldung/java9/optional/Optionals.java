package com.baeldung.optional;

public class Optionals {
    public static String getName(com.google.common.base.Optional<String> name) {
        return name.or("No name given");
    }
    
    public static void main(String[] args) {
        System.out.println("Test");
    }
}