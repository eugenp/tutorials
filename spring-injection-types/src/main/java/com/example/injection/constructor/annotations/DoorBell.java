package com.example.injection.constructor.annotations;

import org.springframework.stereotype.Component;

@Component
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
