package com.baeldung.jackson.deductionbasedpolymorphism;

public class NamedCharacter implements Character {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
