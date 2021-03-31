package com.baeldung.dddsimplehexagonal.domain.port.incoming;

import com.baeldung.dddsimplehexagonal.domain.VehicleSpeedData;

public interface SpeedDataIncomingPort {
    void addSpeedData(VehicleSpeedData vehicleSpeedData) throws Exception;
}
