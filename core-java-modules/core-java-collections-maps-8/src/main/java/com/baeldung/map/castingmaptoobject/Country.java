package com.baeldung.map.castingmaptoobject;

public class Country {
    private String name;

    public Country() {
        // default constructor needed for Jackson deserialization
    }

    public Country(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
