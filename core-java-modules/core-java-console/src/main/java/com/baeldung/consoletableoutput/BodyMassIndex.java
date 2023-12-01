package com.baeldung.consoletableoutput;

public class BodyMassIndex {

    private String name;
    private double height;
    private double weight;

    public BodyMassIndex(String name, double height, double weight) {
        this.name = name;
        this.height = height;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double calculate() {
        double bmi = weight / (height * height);
        String formattedBmi = String.format("%.2f", bmi);
        return Double.parseDouble(formattedBmi);
    }
}
