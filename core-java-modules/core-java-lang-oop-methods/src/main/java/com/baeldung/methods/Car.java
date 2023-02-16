package com.baeldung.methods;

public class Car {

    private final String make;
    private final String model;
    private final int year;
    private final String color;
    private final boolean automatic;
    private final int numDoors;
    private final String features;

    private Car(Builder builder) {
        this.make = builder.make;
        this.model = builder.model;
        this.year = builder.year;
        this.color = builder.color;
        this.automatic = builder.automatic;
        this.numDoors = builder.numDoors;
        this.features = builder.features;
    }

    public static class Builder {
        // required fields
        private final String make;
        private final String model;
        private final int year;
        // optional fields
        private String color = "unknown";
        private boolean automatic = false;
        private int numDoors = 4;
        private String features = "";

        public Builder(String make, String model, int year) {
            this.make = make;
            this.model = model;
            this.year = year;
        }

        public Builder color(String color) {
            this.color = color;
            return this;
        }

        public Builder automatic(boolean automatic) {
            this.automatic = automatic;
            return this;
        }

        public Builder numDoors(int numDoors) {
            this.numDoors = numDoors;
            return this;
        }

        public Car build() {
            return new Car(this);
        }
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public String getColor() {
        return color;
    }

    public boolean isAutomatic() {
        return automatic;
    }

    public int getNumDoors() {
        return numDoors;
    }

    public String getFeatures() {
        return features;
    }

}
