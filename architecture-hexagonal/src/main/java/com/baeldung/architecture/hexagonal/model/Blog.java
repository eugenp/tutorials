package com.baeldung.architecture.hexagonal.model;

public class Blog {

    Long id;
    String name;

    public Blog(final Long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
