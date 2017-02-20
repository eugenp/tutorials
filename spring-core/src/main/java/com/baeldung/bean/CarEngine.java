package com.baeldung.bean;

public class CarEngine {

    private int volume;

    public CarEngine(int volume) {
        this.volume = volume;
    }

    public int getVolume() {
        return volume;
    }

    @Override
    public String toString() {
        return "CarEngine [volume=" + volume + "]";
    }

}
