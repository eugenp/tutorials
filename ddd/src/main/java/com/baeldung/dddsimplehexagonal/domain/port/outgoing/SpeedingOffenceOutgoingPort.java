package com.baeldung.dddsimplehexagonal.domain.port.outgoing;

import com.baeldung.dddsimplehexagonal.domain.VehicleSpeedData;

public interface SpeedingOffenceOutgoingPort {
    void addSpeedingOffenseData(VehicleSpeedData vehicleSpeedData) throws Exception;
}
