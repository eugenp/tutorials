package com.baeldung.jackson.deduction_based_polymorphism;

public class King extends NamedCharacter {

    private String land;

    public String getLand() {
        return land;
    }

    public void setLand(String land) {
        this.land = land;
    }
}
