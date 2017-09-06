package com.example.injection.property.annotations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DoorBellButton implements PushButton {
    
    @Autowired
    private Device device;
    
    public void press() {
        device.operate();
    }

}
