package com.baeldung.staticmethods;

import org.apache.commons.lang3.StringUtils;

public class Car {

    private int id;
    private String model;

    public static String getMake() {
        return "Skoda";
    }

    public static void main(String[] args) {
        Car car1 = new Car(1, "Karoq");
        Car car2 = new Car(2, "Kodiaq");
    }

    public Car(int id, String model) {
        this.id = id;
        this.model = model;
    }

    public int getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public String getModelCapitalized() {
        return StringUtils.capitalize(model);
    }

}
