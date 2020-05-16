package com.baeldung.exceptions;

public class Zoo {
    public Animal animal;

    public Zoo() {
    }
}

abstract class Animal {
    public String name;

    public Animal() {
    }
}

class Cat extends Animal {
    public int lives;

    public Cat() {
    }
}