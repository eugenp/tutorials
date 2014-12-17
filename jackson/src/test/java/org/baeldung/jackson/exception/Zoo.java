package org.baeldung.jackson.exception;

public class Zoo {
    public Animal animal;
}

abstract class Animal {
    public String name;

    protected Animal() {
    }
}

class Cat extends Animal {
    public int lives;

    public Cat() {
    }
}