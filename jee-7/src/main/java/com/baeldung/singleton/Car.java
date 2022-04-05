package com.baeldung.singleton;

public class Car {
    
    private String type;
    private String model;
    private boolean serviced = false;
    
    public Car(String type, String model) {
        super();
        this.type = type;
        this.model = model;
    }

    public boolean isServiced () {
        return serviced;
    }
    
    public void setServiced(Boolean serviced) {
        this.serviced = serviced;
    }

    @Override
    public String toString() {
        return "Car [type=" + type + ", model=" + model + "]";
    }
    
}
