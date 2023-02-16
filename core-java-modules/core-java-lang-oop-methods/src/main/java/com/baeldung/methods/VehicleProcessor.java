package com.baeldung.methods;

public interface VehicleProcessor {

    Vehicle processVehicle(String manufacturer, String model, String color, int weight, boolean status);

    Vehicle processVehicle(Vehicle vehicle);

    Car processCar(Car car);

}
