package com.baeldung.concreteproxy;

import jakarta.persistence.Entity;

@Entity
class Slytherin extends HogwartsHouse {

    private boolean heirOfSlytherin;

    Slytherin() {
    }

    Slytherin(String founder, String houseColors, boolean heirOfSlytherin) {
        super(founder, houseColors);
        this.heirOfSlytherin = heirOfSlytherin;
    }

    boolean isHeirOfSlytherin() {
        return heirOfSlytherin;
    }

    void setHeirOfSlytherin(boolean heirOfSlytherin) {
        this.heirOfSlytherin = heirOfSlytherin;
    }
}