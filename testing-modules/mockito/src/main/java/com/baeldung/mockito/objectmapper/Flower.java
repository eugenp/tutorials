package com.baeldung.mockito.objectmapper;

public class Flower {

    private String name;
    private Integer petals;

    public Flower(String name, Integer petals) {
        this.name = name;
        this.petals = petals;
    }

    public Flower() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPetals() {
        return petals;
    }

    public void setPetals(Integer petals) {
        this.petals = petals;
    }

}
