package com.example.injection.property.annotations;

import org.springframework.stereotype.Component;

@Component
public class DoorBell implements Device {

    public void operate() {
        System.out.println("tick tick");
    }

}
