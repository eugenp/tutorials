package com.baeldung.guava.entity;

import com.google.common.base.MoreObjects;

public class Administrator extends User{
    public Administrator(long id, String name, int age) {
        super(id, name, age);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", getId())
                .add("name", getName())
                .add("age", getAge())
                .toString();
    }
}