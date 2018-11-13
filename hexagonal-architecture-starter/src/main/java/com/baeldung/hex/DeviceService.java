package com.baeldung.hex;

import com.baeldung.hex.port.DeviceInterfacePort;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {

    public void manageDevice(String device, DeviceInterfacePort deviceInterfacePort) {

        if (device.equals("smartphone")) {
            deviceInterfacePort.setSmartphoneAction();
        } else {
            deviceInterfacePort.setOtherDevicesAction();
        }

    }

}
