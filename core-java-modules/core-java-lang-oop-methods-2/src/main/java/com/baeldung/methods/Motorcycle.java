package com.baeldung.methods;

import java.io.Serializable;

public class Motorcycle extends Vehicle implements Serializable {

    private static final long serialVersionUID = 5973661295933599929L;

    private int year;
    private String features = "";

    public Motorcycle() {
        super();
    }

    public Motorcycle(String make, String model, String color, int weight, boolean statusNew, int year) {
        super(make, model, color, weight, statusNew);
        this.year = year;
    }

    public Motorcycle(Vehicle vehicle, int year) {
        super(vehicle);
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getFeatures() {
        return features;
    }

    public void addMotorcycleFeatures(String... features) {
        StringBuilder str = new StringBuilder(this.getFeatures());
        for (String feature : features) {
            if (!str.toString().isEmpty())
                str.append(", ");
            str.append(feature);
        }
        this.setFeatures(str.toString());
    }

}
