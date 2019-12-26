package com.baeldung.java.reflection;

public abstract class Animal implements Eating {

    public static final String CATEGORY = "domestic";

    private String name;

    public Animal(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected abstract String getSound();

}
