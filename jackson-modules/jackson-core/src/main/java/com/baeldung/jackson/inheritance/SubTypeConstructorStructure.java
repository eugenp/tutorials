package com.baeldung.jackson.inheritance;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SubTypeConstructorStructure {
    public static class Fleet {
        private List<Vehicle> vehicles;

        public List<Vehicle> getVehicles() {
            return vehicles;
        }

        public void setVehicles(List<Vehicle> vehicles) {
            this.vehicles = vehicles;
        }
    }

    public static abstract class Vehicle {
        private String make;
        private String model;

        protected Vehicle(String make, String model) {
            this.make = make;
            this.model = model;
        }

        public String getMake() {
            return make;
        }

        public void setMake(String make) {
            this.make = make;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }
    }

    public static class Car extends Vehicle {
        private int seatingCapacity;
        private double topSpeed;

        @JsonCreator
        public Car(@JsonProperty("make") String make, @JsonProperty("model") String model, @JsonProperty("seating") int seatingCapacity, @JsonProperty("topSpeed") double topSpeed) {
            super(make, model);
            this.seatingCapacity = seatingCapacity;
            this.topSpeed = topSpeed;
        }

        public int getSeatingCapacity() {
            return seatingCapacity;
        }

        public void setSeatingCapacity(int seatingCapacity) {
            this.seatingCapacity = seatingCapacity;
        }

        public double getTopSpeed() {
            return topSpeed;
        }

        public void setTopSpeed(double topSpeed) {
            this.topSpeed = topSpeed;
        }
    }

    public static class Truck extends Vehicle {
        private double payloadCapacity;

        @JsonCreator
        public Truck(@JsonProperty("make") String make, @JsonProperty("model") String model, @JsonProperty("payload") double payloadCapacity) {
            super(make, model);
            this.payloadCapacity = payloadCapacity;
        }

        public double getPayloadCapacity() {
            return payloadCapacity;
        }

        public void setPayloadCapacity(double payloadCapacity) {
            this.payloadCapacity = payloadCapacity;
        }
    }
}