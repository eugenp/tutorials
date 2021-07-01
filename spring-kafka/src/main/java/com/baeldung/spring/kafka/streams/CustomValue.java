package com.baeldung.spring.kafka.streams;

public class CustomValue {

    private String name;

    public CustomValue(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}