package com.baeldung.dddsimplehexagonal.domain;

public class VehicleSpeedRecord {
    
    private String registrationPlateNo;
    private float speed;
    
    public VehicleSpeedRecord(String registrationPlateNo, float speed) {
        this.registrationPlateNo = registrationPlateNo;
        this.speed = speed;
    }
    
    public boolean aboveSpeedLimit(float speedLimit) {
        return speed > speedLimit;
    }
    
    public String getRegistrationPlateNo() {
        return registrationPlateNo;
    }

    public float getSpeed() {
        return speed;
    }
    
    private VehicleSpeedRecord() {
    }
}
