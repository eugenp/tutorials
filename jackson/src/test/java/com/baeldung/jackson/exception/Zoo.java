package com.baeldung.jackson.exception;

public class Zoo {
    public Animal animal;

    public Zoo() {
    }
}

abstract class Animal {
    public String name;

    Animal() {
    }
}

class Cat extends Animal {
    public int lives;

    public Cat() {
    }
}