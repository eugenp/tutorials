package com.baeldung.objectcopy.objects;

public class Person {
    private String name;
    private int score;

    public Person(String name, int score) {
        this.name = name;
        this.score = score;
    }

    // Deep copy constructor
    public Person(Person other) {
        this.name = other.name;
        this.score = other.score;
    }

    // Getters and setters

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
