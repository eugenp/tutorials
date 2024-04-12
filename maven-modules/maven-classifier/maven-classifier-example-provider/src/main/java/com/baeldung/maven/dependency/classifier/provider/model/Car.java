package com.baeldung.maven.dependency.classifier.provider.model;

public class Car {
    private Type type;
    private PowerSource fuelType;

    public Type getType() {
        return this.type;
    }

    public void setType(Type carType) {
        this.type = carType;
    }

    public PowerSource getPowerSource() {
        return this.fuelType;
    }

    public void setFuelType(PowerSource fuelType) {
        this.fuelType = fuelType;
    }

    public enum Type {
        ELECTRIC
    }
}
