package com.baeldung.beaninjection.domain;

public class Advertisement {
    private String description;

    public Advertisement(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("%s", description);
    }
}
