package com.baeldung.domain;


import java.util.Objects;

public class Food {
    private final String name;
    private int amount;
    private final Unit unit;

    public Food(String name, int amount, Unit unit) {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
    }

    @Override
    public String toString() {
        return this.amount + " " + this.unit.toString() + " " + this.name;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Food))
            return false;
        Food other = (Food) o;
        return Objects.equals(name, other.name) && Objects.equals(unit, other.unit);
    }

}
