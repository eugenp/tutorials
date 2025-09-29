package com.baeldung.flexibleconstructorbodies;

public class Coffee {

    int water;
    int milk;

    public Coffee(int water, int milk) {
        this.water = water;
        this.milk = milk;
    }

    public int getTotalVolume() {
        return water + milk;
    }

}
