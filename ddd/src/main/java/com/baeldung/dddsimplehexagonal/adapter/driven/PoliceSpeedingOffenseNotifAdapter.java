package com.baeldung.dddsimplehexagonal.adapter.driven;

import com.baeldung.dddsimplehexagonal.adapter.driven.exception.DrivenAdapterRuntimeException;
import com.baeldung.dddsimplehexagonal.domain.port.outgoing.OutgoingSpeedDataDTO;
import com.baeldung.dddsimplehexagonal.domain.port.outgoing.SpeedingOffenceOutgoingPort;
import com.baeldung.dddsimplehexagonal.external.driven.PoliceSpeedingOffenseNotifInterface;

public class PoliceSpeedingOffenseNotifAdapter implements SpeedingOffenceOutgoingPort {
    
    PoliceSpeedingOffenseNotifInterface speedingOffenseNotifSystem;

    @Override
    public void addSpeedingOffenseData(OutgoingSpeedDataDTO outgoingSpeedDataDTO) {
        
        String registrationPlateNo = outgoingSpeedDataDTO.getRegistrationPlateNo();
        if (registrationPlateNo == null) {
            throw new DrivenAdapterRuntimeException("registrationPlateNo in given outgoing DTO object can't be null");
        }
        
        float speed = outgoingSpeedDataDTO.getSpeed();
        if (speed < 0) {
            throw new DrivenAdapterRuntimeException("speed in given outgoing DTO object can't be negative");
        }
        
        float speedLimit = outgoingSpeedDataDTO.getSpeedLimit();
        if (speedLimit < 0) {
            throw new DrivenAdapterRuntimeException("speedLimit in given outgoing DTO object can't be negative");
        }
        
        String speedingOffenseData = PoliceSpeedingOffenseNotifInterface.createSpeedingOffenseNotifData(
          registrationPlateNo, speed, speedLimit);
            
        speedingOffenseNotifSystem.sendSpeedingOffenseNotification(speedingOffenseData);
    }
    
    public void setSpeedingOffenseNotifSystem(PoliceSpeedingOffenseNotifInterface speedingOffenseNotifSystem) {
        this.speedingOffenseNotifSystem = speedingOffenseNotifSystem;
    }
}
