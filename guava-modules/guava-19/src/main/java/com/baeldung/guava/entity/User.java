package com.baeldung.guava.entity;

import com.google.common.base.MoreObjects;

public class User{
    private long id;
    private String name;
    private int age;

    public User(long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(User.class)
                .add("id", id)
                .add("name", name)
                .add("age", age)
                .toString();
    }
}