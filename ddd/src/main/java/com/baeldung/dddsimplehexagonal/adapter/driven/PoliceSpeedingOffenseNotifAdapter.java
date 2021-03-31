package com.baeldung.dddsimplehexagonal.adapter.driven;

import com.baeldung.dddsimplehexagonal.domain.VehicleSpeedData;
import com.baeldung.dddsimplehexagonal.domain.port.outgoing.SpeedingOffenceOutgoingPort;
import com.baeldung.dddsimplehexagonal.external.driven.PoliceSpeedingOffenseNotifInterface;

public class PoliceSpeedingOffenseNotifAdapter implements SpeedingOffenceOutgoingPort {
    
    PoliceSpeedingOffenseNotifInterface speedingOffenseNotifSystem;

    @Override
    public void addSpeedingOffenseData(VehicleSpeedData vehicleSpeedData) {
        
        String registrationPlateNo = vehicleSpeedData.getRegistrationPlateNo();
        float speed = vehicleSpeedData.getSpeed();
        float speedLimit = vehicleSpeedData.getSpeedLimit();
        
        String speedingOffenseData = PoliceSpeedingOffenseNotifInterface.createSpeedingOffenseNotifData(
          registrationPlateNo, speed, speedLimit);
        speedingOffenseNotifSystem.sendSpeedingOffenseNotification(speedingOffenseData);
    }
    
    public void setSpeedingOffenseNotifSystem(PoliceSpeedingOffenseNotifInterface speedingOffenseNotifSystem) {
        this.speedingOffenseNotifSystem = speedingOffenseNotifSystem;
    }
}
