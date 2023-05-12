package com.baeldung.methods;

public class Car {

    private final String make;
    private final String model;
    private final int year;
    private final String color;
    private final boolean automatic;
    private final int numDoors;
    private final String features;

    private Car(CarBuilder carBuilder) {
        this.make = carBuilder.make;
        this.model = carBuilder.model;
        this.year = carBuilder.year;
        this.color = carBuilder.color;
        this.automatic = carBuilder.automatic;
        this.numDoors = carBuilder.numDoors;
        this.features = carBuilder.features;
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

    public static class CarBuilder {

        private final String make;
        private final String model;
        private final int year;

        private String color = "unknown";
        private boolean automatic = false;
        private int numDoors = 4;
        private String features = "";

        public CarBuilder(String make, String model, int year) {
            this.make = make;
            this.model = model;
            this.year = year;
        }

        public CarBuilder color(String color) {
            this.color = color;
            return this;
        }

        public CarBuilder automatic(boolean automatic) {
            this.automatic = automatic;
            return this;
        }

        public CarBuilder numDoors(int numDoors) {
            this.numDoors = numDoors;
            return this;
        }

        public CarBuilder features(String features) {
            this.features = features;
            return this;
        }

        public Car build() {
            return new Car(this);
        }
    }

    @Override
    public String toString() {
        return "Car [make=" + make + ", model=" + model + ", year=" + year + ", color=" + color + ", automatic=" + automatic + ", numDoors=" + numDoors + ", features=" + features + "]";
    }

}
