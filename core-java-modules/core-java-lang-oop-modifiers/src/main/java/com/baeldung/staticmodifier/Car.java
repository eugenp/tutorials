package com.baeldung.staticmodifier;

/**
 * This class demonstrates the use of static fields and static methods
 * the instance variables engine and displacement are distinct for 
 * each and every object whereas static/class variable numberOfCars
 * is unique and is shared across all objects of this class.
 * 
 * @author baeldung
 *
 */
public class Car {
    private String name;
    private String engine;
    
    public static int numberOfCars;
    
    public Car(String name, String engine) {
        this.name = name;
        this.engine = engine;
        numberOfCars++;
    }

    //getters and setters
    public static int getNumberOfCars() {
        return numberOfCars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public static String getCarsInformation(Car car) {
        return car.getName() + "-" + car.getEngine();
    }

    public static void setNumberOfCars(int numberOfCars) {
        Car.numberOfCars = numberOfCars;
    }
}
