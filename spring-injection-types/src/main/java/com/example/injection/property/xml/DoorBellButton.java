package com.example.injection.property.xml;

public class DoorBellButton implements PushButton {
    
    private Device device;
    
    public void press() {
        device.operate();
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}
