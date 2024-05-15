package com.baeldung.streams.reduce.entities;

public class User {

    private final String name;
    private final int age;
    private final Rating rating = new Rating();

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Rating getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "User{" + "name=" + name + ", age=" + age + '}';
    }
}
