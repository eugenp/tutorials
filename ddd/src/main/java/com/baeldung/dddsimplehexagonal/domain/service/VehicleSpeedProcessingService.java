package com.baeldung.dddsimplehexagonal.domain.service;

import com.baeldung.dddsimplehexagonal.domain.VehicleSpeedRecord;
import com.baeldung.dddsimplehexagonal.domain.port.incoming.IncomingSpeedDataDTO;
import com.baeldung.dddsimplehexagonal.domain.port.incoming.SpeedDataIncomingPort;
import com.baeldung.dddsimplehexagonal.domain.port.outgoing.OutgoingSpeedDataDTO;
import com.baeldung.dddsimplehexagonal.domain.port.outgoing.SpeedingOffenceOutgoingPort;

public class VehicleSpeedProcessingService implements SpeedDataIncomingPort {
    
    private SpeedingOffenceOutgoingPort outgoingPortAdapter;

    @Override
    public void addSpeedData(IncomingSpeedDataDTO incomingSpeedDataDTO) {

        String registrationPlateNo = incomingSpeedDataDTO.getRegistrationPlateNo();
        float speed = incomingSpeedDataDTO.getSpeed();
        float speedLimit = incomingSpeedDataDTO.getSpeedLimit();
        
        VehicleSpeedRecord speedRecord = new VehicleSpeedRecord(
          registrationPlateNo, speed);
        
        if (speedRecord.aboveSpeedLimit(speedLimit)) {
            OutgoingSpeedDataDTO outgoingSpeedDataDTO = new OutgoingSpeedDataDTO(
                registrationPlateNo, speed, speedLimit);
            outgoingPortAdapter.addSpeedingOffenseData(outgoingSpeedDataDTO);
        }
    }

    public void setOutgoingPortAdapter(SpeedingOffenceOutgoingPort outgoingPortAdapter) {
        this.outgoingPortAdapter = outgoingPortAdapter;
    }
}
