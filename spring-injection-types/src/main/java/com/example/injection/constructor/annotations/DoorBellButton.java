package com.example.injection.constructor.annotations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DoorBellButton implements PushButton {
    
    private Device device;
    
    @Autowired
    public DoorBellButton(Device device) {
        this.device = device;
    }
    
    public void press() {
        device.operate();
    }

}
