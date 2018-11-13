package com.baeldung.hex;

import com.baeldung.hex.adapter.DeviceResponseAdapter;
import org.springframework.web.bind.annotation.*;

@RestController
public class DeviceController {

    private DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping("/api/v2/devices/device/{device}")
    public String deviceType(@PathVariable String device) {

        DeviceResponseAdapter deviceResponseAdapter = new DeviceResponseAdapter();
        deviceService.manageDevice(device, deviceResponseAdapter);
        return deviceResponseAdapter.getAction();

    }


}
