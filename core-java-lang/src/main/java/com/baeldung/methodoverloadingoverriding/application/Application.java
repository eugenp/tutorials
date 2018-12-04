package com.baeldung.methodoverloadingoverriding.application;

import com.baeldung.methodoverloadingoverriding.model.Car;
import com.baeldung.methodoverloadingoverriding.model.Vehicle;
import com.baeldung.methodoverloadingoverriding.util.Multiplier;

public class Application {
    
    public static void main(String[] args) {
        Multiplier multiplier = new Multiplier();
        System.out.println(multiplier.multiply(10, 10));
        System.out.println(multiplier.multiply(10, 10, 10));
        System.out.println(multiplier.multiply(10, 10.5));
        System.out.println(multiplier.multiply(10.5, 10.5));
        
        Vehicle vehicle = new Vehicle();
        System.out.println(vehicle.accelerate(100));
        System.out.println(vehicle.run());
        System.out.println(vehicle.stop());
        
        Vehicle car = new Car();
        System.out.println(car.accelerate(80));
        System.out.println(car.run());
        System.out.println(car.stop());
    }
}
