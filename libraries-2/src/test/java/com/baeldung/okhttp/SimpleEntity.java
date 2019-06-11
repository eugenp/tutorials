package com.baeldung.okhttp;

public class SimpleEntity {
    protected String name;

    public SimpleEntity(String name) {
        this.name = name;
    }

    //no-arg constructor, getters and setters here
    
    public SimpleEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
