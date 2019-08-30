package com.baeldung.dependencyinjectiontypes.model;

public class Car extends Vehicle {
    private String engineType;

    public Car(String name, String manufacturer, String engineType) {
        super(name, manufacturer);
        this.engineType = engineType;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    @Override
    public String toString() {
        return "Car{" +
                "engineType='" + engineType + '\'' +
                '}';
    }
}
