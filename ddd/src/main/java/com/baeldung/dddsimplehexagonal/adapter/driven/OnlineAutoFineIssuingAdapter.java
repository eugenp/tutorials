package com.baeldung.dddsimplehexagonal.adapter.driven;

import com.baeldung.dddsimplehexagonal.domain.VehicleSpeedData;
import com.baeldung.dddsimplehexagonal.domain.port.outgoing.SpeedingOffenceOutgoingPort;
import com.baeldung.dddsimplehexagonal.external.driven.OnlineAutoFineIssuingInterface;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OnlineAutoFineIssuingAdapter implements SpeedingOffenceOutgoingPort {
    
    private OnlineAutoFineIssuingInterface onlineFineIssuingSystem;

    @Override
    public void addSpeedingOffenseData(VehicleSpeedData vehicleSpeedData) throws Exception {
        String fineJsonStr = new ObjectMapper().writeValueAsString(vehicleSpeedData);
        onlineFineIssuingSystem.sendAutoFineIssuingJsonStr(fineJsonStr);
    }
    
    public void setOnlineFineIssuingSystem(OnlineAutoFineIssuingInterface onlineFineIssuingSystem) {
        this.onlineFineIssuingSystem = onlineFineIssuingSystem;
    }
}
