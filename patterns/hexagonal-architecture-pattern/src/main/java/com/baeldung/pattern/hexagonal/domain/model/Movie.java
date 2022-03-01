package com.baeldung.pattern.hexagonal.domain.model;

public class Movie {

    private String id;
    private String name;

    public Movie() {
        super();
    }

    public Movie(String name) {
        this.name = name;
    }

    public Movie(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}