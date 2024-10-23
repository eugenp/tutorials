package com.baeldung.primary;

public class Car {

    long id;
    String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Car(String name) {
        this.name = name;
    }
}
