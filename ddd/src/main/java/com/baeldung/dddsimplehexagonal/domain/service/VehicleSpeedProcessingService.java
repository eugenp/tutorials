package com.baeldung.dddsimplehexagonal.domain.service;

import com.baeldung.dddsimplehexagonal.domain.VehicleSpeedData;
import com.baeldung.dddsimplehexagonal.domain.port.incoming.SpeedDataIncomingPort;
import com.baeldung.dddsimplehexagonal.domain.port.outgoing.SpeedingOffenceOutgoingPort;

public class VehicleSpeedProcessingService implements SpeedDataIncomingPort {
    
    private SpeedingOffenceOutgoingPort outgoingPortAdapter;

    @Override
    public void addSpeedData(VehicleSpeedData vehicleSpeedData) throws Exception {
        if (vehicleSpeedData.aboveSpeedLimit()) {
            outgoingPortAdapter.addSpeedingOffenseData(vehicleSpeedData);
        }
    }

    public void setOutgoingPortAdapter(SpeedingOffenceOutgoingPort outgoingPortAdapter) {
        this.outgoingPortAdapter = outgoingPortAdapter;
    }
}
