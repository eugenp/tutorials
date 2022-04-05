package com.baeldung.jpa.enums;

import java.util.stream.Stream;

public enum Priority {
    LOW(100), MEDIUM(200), HIGH(300);

    private int priority;

    private Priority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public static Priority of(int priority) {
        return Stream.of(Priority.values())
                .filter(p -> p.getPriority() == priority)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
