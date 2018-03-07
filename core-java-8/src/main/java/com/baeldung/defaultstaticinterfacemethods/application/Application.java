package com.baeldung.defaultstaticinterfacemethods.application;

import com.baeldung.defaultstaticinterfacemethods.model.Car;
import com.baeldung.defaultstaticinterfacemethods.model.Vehicle;

public class Application {

    public static void main(String[] args) {

        Vehicle car = new Car("BMW");
        System.out.println(car.getBrand());
        System.out.println(car.speedUp());
        System.out.println(car.slowDown());
        System.out.println(car.turnAlarmOn());
        System.out.println(car.turnAlarmOff());
        System.out.println(Vehicle.getHorsePower(2500, 480));
    }
}