package com.baeldung.jackson.inheritance;

import java.util.List;

public class TypeInfoStructure {
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

        protected Vehicle() {
        }

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

        public Car() {
        }

        public Car(String make, String model, int seatingCapacity, double topSpeed) {
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

        public Truck() {
        }

        public Truck(String make, String model, double payloadCapacity) {
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