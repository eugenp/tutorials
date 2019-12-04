package com.baeldung.hexagon.articles.domain.model;

public class Title {
    private final String value;

    private Title(final String value) {
        this.value = value;
    }

    public static Title of(final String title) {
        return new Title(title);
    }

    public String value() {
        return value;
    }
}
