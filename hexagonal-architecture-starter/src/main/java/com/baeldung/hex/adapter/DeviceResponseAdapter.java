package com.baeldung.hex.adapter;

import com.baeldung.hex.port.DeviceInterfacePort;

public class DeviceResponseAdapter implements DeviceInterfacePort {

    private String action;

    @Override
    public void setSmartphoneAction() {
        this.action = "Here actions for smartphone devices";
    }

    @Override
    public void setOtherDevicesAction() {
        this.action = "Here actions for others devices";
    }

    public String getAction() {
        return action;
    }

}
