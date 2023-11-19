package com.baeldung.jersey.server.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Fruit {

    @Min(value = 10, message = "Fruit weight must be 10 or greater")
    private Integer weight;
    @Size(min = 5, max = 200)
    private String name;
    @Size(min = 5, max = 200)
    private String colour;
    private String serial;

    public Fruit() {
    }

    public Fruit(String name, String colour) {
        this.name = name;
        this.colour = colour;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getColour() {
        return colour;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
    
    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }
    
    @Override
    public String toString() {
        return "Fruit [name: " + getName() + " colour: " + getColour() + "]";
    }
}
