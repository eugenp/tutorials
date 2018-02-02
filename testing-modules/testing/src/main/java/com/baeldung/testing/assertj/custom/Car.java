package com.baeldung.testing.assertj.custom;

public class Car {
    private String type;
    private Person owner;

    public Car(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }
}
