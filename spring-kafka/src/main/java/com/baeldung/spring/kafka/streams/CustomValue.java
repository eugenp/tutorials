package com.baeldung.spring.kafka.streams;

public class CustomValue {

    private String name;

    public CustomValue(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}