package com.baeldung.dropwizard.introduction.domain;

public class Brand {
    private final Long id;
    private final String name;

    public Brand(final Long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
