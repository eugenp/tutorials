package com.baeldung.shallowvsdeep;

public class Pilot {

    private String name;

    public Pilot deepClone() {
        return new Pilot(name);
    }

    public Pilot(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
