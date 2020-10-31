package com.baeldung.sealed.alternative;

public class Vehicles {

    abstract static class Vehicle {

        private String registrationNumber;

        public String getRegistrationNumber() {
            return registrationNumber;
        }

        public void setRegistrationNumber(String registrationNumber) {
            this.registrationNumber = registrationNumber;
        }

    }

    public static final class Car extends Vehicle {
    }

    public static final class Truck extends Vehicle {
    }

}
