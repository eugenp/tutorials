package com.baeldung.lexer;

import java.util.Optional;

public class Expression {

    private final String value;
    private int index = 0;

    public Expression(String value) {
        this.value = value != null ? value : "";
    }

    public Optional<Character> next() {
        if (index >= value.length()) {
            return Optional.empty();
        }
        return Optional.of(value.charAt(index++));
    }

    public boolean hasNext() {
        return index < value.length();
    }

    public String getValue() {
        return value;
    }
}

