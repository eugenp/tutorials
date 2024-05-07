package com.baeldung.elvisoperator;

import java.util.Optional;

public class ElvisOperatorUtil {

    public static String usingOptional(User user) {
        String name = user.getName();
        String greeting = Optional.ofNullable(name)
            .map(String::toUpperCase) // Transform if present
            .orElse("Hello Stranger");

        return greeting;
    }

    public static String usingTernaryOperator(User user) {
        String greeting = (user != null && user.getName() != null) ? user.getName() : "Hello, Stranger";
        return greeting;
    }

    public static <T> T elvis(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }
}
