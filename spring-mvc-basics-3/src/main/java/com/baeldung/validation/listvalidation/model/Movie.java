package com.baeldung.validation.listvalidation.model;

import java.util.UUID;

import javax.validation.constraints.NotEmpty;

public class Movie {

    private String id;

    @NotEmpty(message = "Movie name cannot be empty.")
    private String name;
    
    public Movie(String name) {
        this.id = UUID.randomUUID()
            .toString();
        this.name = name;
    }

    public Movie(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
