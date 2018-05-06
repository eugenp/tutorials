package com.baeldung.java9.optional;

public class Optionals {
    public static String getName(com.google.common.base.Optional<String> name) {
        return name.or("No name given");
    }
}