package com.baeldung.jersey.server.model;

public class Fruit {

    private final String name;
    private final String colour;

    public Fruit(String name, String colour) {
        this.name = name;
        this.colour = colour;
    }
    
    public String getName() {
        return name;
    }

    public String getColour() {
        return colour;
    }

    

}
