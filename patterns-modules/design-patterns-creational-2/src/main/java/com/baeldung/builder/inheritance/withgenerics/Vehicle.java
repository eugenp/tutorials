package com.baeldung.builder.inheritance.withgenerics;

public class Vehicle {

    private String colour;
    private String fuelType;

    public Vehicle(Builder builder) {
        this.colour = builder.colour;
        this.fuelType = builder.fuelType;
    }

    public String getColour() {
        return colour;
    }

    public String getFuelType() {
        return fuelType;
    }

    public static class Builder<T extends Builder> {

        protected String colour;
        protected String fuelType;

        T self() {
            return (T) this;
        }

        public T colour(String colour) {
            this.colour = colour;
            return self();
        }

        public T fuelType(String fuelType) {
            this.fuelType = fuelType;
            return self();
        }

        public Vehicle build() {
            return new Vehicle(this);
        }
    }
}
