package com.example.injection.constructor.xml;

public class DoorBell implements Device {

    private int ringCount;

    public void operate() {
        System.out.println("tick tick");
        ringCount++;
    }

    public int getRingCount() {
        return ringCount;
    }

}
