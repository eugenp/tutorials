package com.baeldung.sealed.classes;

public class VehicleTest {

    private int getPropertyTraditionalWay(Vehicle vehicle) {
        if (vehicle instanceof Car) {
            return ((Car) vehicle).getNumberOfSeats();
        } else if (vehicle instanceof Truck) {
            return ((Truck) vehicle).getLoadCapacity();
        } else {
            throw new RuntimeException("Unknown instance of Vehicle");
        }
    }

    private int getPropertyPatternMatching(Vehicle vehicle) {
        if (vehicle instanceof Car car) {
            return car.getNumberOfSeats();
        } else if (vehicle instanceof Truck truck) {
            return truck.getLoadCapacity();
        } else {
            throw new RuntimeException("Unknown instance of Vehicle");
        }
    }

}
