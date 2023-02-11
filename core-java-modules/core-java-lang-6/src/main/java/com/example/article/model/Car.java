package com.example.article.model;

import java.util.ArrayList;
import java.util.List;

public class Car implements Cloneable {

    private List<String> accessories;

    public Car(List<String> accessories) {
        this.accessories = accessories;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Car deepCopy() {
        List<String> items = new ArrayList<>();
        accessories.forEach(i -> items.add(i));

        Car car = new Car(items);
        return car;
    }

    public List<String> getAccessories() {
        return accessories;
    }
}
