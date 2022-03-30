package com.baeldung.jackson.deductionbasedpolymorphism;

public class KingV1 implements CharacterV1 {
    private String land;

    public String getLand() {
        return land;
    }

    public void setLand(String land) {
        this.land = land;
    }
}
