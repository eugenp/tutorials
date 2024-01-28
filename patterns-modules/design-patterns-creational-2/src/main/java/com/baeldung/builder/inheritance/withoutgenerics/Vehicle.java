package com.baeldung.builder.inheritance.withoutgenerics;

public class Vehicle {

    private String fuelType;
    private String colour;

    public String getFuelType() {
        return fuelType;
    }

    public String getColour() {
        return colour;
    }

    public Vehicle(VehicleBuilder builder) {
        this.colour = builder.colour;
        this.fuelType = builder.fuelType;

    }

    public static class VehicleBuilder {

        protected String fuelType;
        protected String colour;

        public VehicleBuilder fuelType(String fuelType) {
            this.fuelType = fuelType;
            return this;
        }

        public VehicleBuilder colour(String colour) {
            this.colour = colour;
            return this;
        }

        public Vehicle build() {
            return new Vehicle(this);
        }
    }
}
