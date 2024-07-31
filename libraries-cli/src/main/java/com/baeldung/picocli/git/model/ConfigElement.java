package com.baeldung.picocli.git.model;

import java.util.Arrays;

public enum ConfigElement {
    USERNAME("user.name"),
    EMAIL("user.email");

    private final String value;

    ConfigElement(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static ConfigElement from(String value) {
        return Arrays.stream(values())
          .filter(element -> element.value.equals(value))
          .findFirst()
          .orElseThrow(() -> new IllegalArgumentException("The argument " + value + " doesn't match any ConfigElement"));
    }
}
