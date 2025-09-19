package com.baeldung.flexibleconstructorbodies;

public class SmallCoffee extends Coffee {

    String topping;

    public SmallCoffee(int water, int milk, String topping) {
        int maxCupVolume = 100;
        int totalVolume = water + milk;
        if(totalVolume > maxCupVolume) {
            throw new IllegalArgumentException();
        }
        this.topping = topping;
        super(water, milk);
    }

}
