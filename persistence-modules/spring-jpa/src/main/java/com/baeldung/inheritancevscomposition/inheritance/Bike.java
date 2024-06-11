package com.baeldung.inheritancevscomposition.inheritance;

import jakarta.persistence.Entity;

@Entity
public class Bike extends Vehicle {

    private boolean hasBasket;

    public boolean isHasBasket() {
        return hasBasket;
    }

    public void setHasBasket(boolean hasBasket) {
        this.hasBasket = hasBasket;
    }
}