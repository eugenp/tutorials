package com.baeldung.jackson.inheritance;

public class IgnoranceMixinOrIntrospection {
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

    public static abstract class Car extends Vehicle {
        private int seatingCapacity;
        private double topSpeed;

        protected Car() {
        }

        protected Car(String make, String model, int seatingCapacity, double topSpeed) {
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

    public static class Sedan extends Car {
        public Sedan() {
        }

        public Sedan(String make, String model, int seatingCapacity, double topSpeed) {
            super(make, model, seatingCapacity, topSpeed);
        }
    }

    public static class Crossover extends Car {
        private double towingCapacity;

        public Crossover() {
        }

        public Crossover(String make, String model, int seatingCapacity, double topSpeed, double towingCapacity) {
            super(make, model, seatingCapacity, topSpeed);
            this.towingCapacity = towingCapacity;
        }

        public double getTowingCapacity() {
            return towingCapacity;
        }

        public void setTowingCapacity(double towingCapacity) {
            this.towingCapacity = towingCapacity;
        }
    }
}