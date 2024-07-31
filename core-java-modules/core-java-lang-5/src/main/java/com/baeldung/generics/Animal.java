package com.baeldung.generics;

abstract class Animal {

    protected final String type;
    protected final String name;

    protected Animal(String type, String name) {
        this.type = type;
        this.name = name;
    }

    abstract String makeSound();

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}