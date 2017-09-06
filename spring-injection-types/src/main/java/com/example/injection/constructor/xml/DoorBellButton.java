package com.example.injection.constructor.xml;

public class DoorBellButton implements PushButton {
    
    private Device device;
    
    public DoorBellButton(Device device) {
        this.device = device;
    }
    
    public void press() {
        device.operate();
    }

}
