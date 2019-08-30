package com.baeldung.convertlisttomap;

public class Animal {
    private int id;
    private String name;

    public Animal(int id, String name) {
        this.id = id;
        this.setName(name);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
