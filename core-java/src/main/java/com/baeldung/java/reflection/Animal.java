package com.baeldung.java.reflection;

abstract class Animal implements Eating {

    public static final String CATEGORY = "domestic";

    private String name;

    Animal(String name) {
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
