package com.baeldung.concreteproxy;

import jakarta.persistence.Entity;

@Entity
class Gryffindor extends HogwartsHouse {

    private boolean hasSummonedSword;

    Gryffindor() {
    }

    Gryffindor(String founder, String houseColors, boolean hasSummonedSword) {
        super(founder, houseColors);
        this.hasSummonedSword = hasSummonedSword;
    }

    boolean getHasSummonedSword() {
        return hasSummonedSword;
    }

    void setHasSummonedSword(boolean hasSummonedSword) {
        this.hasSummonedSword = hasSummonedSword;
    }
}