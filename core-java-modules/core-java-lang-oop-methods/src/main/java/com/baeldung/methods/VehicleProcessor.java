package com.baeldung.methods;

public class VehicleProcessor {

    Vehicle processVehicle(String make, String model, String color, int weight, boolean status) {
        return new Vehicle(make, model, color, weight, status);
    }

    Vehicle processVehicle(Vehicle vehicle) {
        return new Vehicle(vehicle);
    }

    Car processCar(Car car) {
        return new Car.CarBuilder(car.getMake(), car.getModel(), car.getYear()).color(car.getColor())
            .automatic(car.isAutomatic())
            .features(car.getFeatures())
            .build();
    }

}