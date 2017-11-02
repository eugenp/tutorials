package com.baeldung.beaninjection.domain;

public class Sword {
    private int attackPoints;

    public Sword(int attackPoints) {
        this.attackPoints = attackPoints;
    }

    @Override
    public String toString() {
        return "Sword{" +
                "attackPoints=" + attackPoints +
                '}';
    }
}
