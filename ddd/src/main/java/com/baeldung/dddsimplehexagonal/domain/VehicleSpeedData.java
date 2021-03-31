package com.baeldung.dddsimplehexagonal.domain;

import java.util.Objects;

public class VehicleSpeedData {
    
    private String registrationPlateNo;
    private float speed;
    private float speedLimit;
    
    public VehicleSpeedData(String registrationPlateNo, float speed, float speedLimit) {
        this.setRegistrationPlateNo(registrationPlateNo);
        this.setSpeed(speed);
        this.setSpeedLimit(speedLimit);
    }
    
    public boolean aboveSpeedLimit() {
        return speed > speedLimit;
    }
    
    @Override
    public boolean equals(Object o) {
        
        if (this == o) return true;
        
        if ((o instanceof VehicleSpeedData) == false) {
            return false;
        }
        
        VehicleSpeedData vsd1 = (VehicleSpeedData)o;
    
        if (this.registrationPlateNo.equals(vsd1.getRegistrationPlateNo()) 
          && (this.speed == vsd1.getSpeed())
          && (this.getSpeedLimit() == vsd1.getSpeedLimit())) {          
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.registrationPlateNo, this.speed, this.speedLimit);
    }
    
    public String getRegistrationPlateNo() {
        return registrationPlateNo;
    }

    public void setRegistrationPlateNo(String registrationPlateNo) {
        this.registrationPlateNo = registrationPlateNo;
    }
    
    public float getSpeed() {
        return speed;
    }
    
    public void setSpeed(float speed) {
        this.speed = speed;
    }
    
    public float getSpeedLimit() {
        return speedLimit;
    }

    public void setSpeedLimit(float speedLimit) {
        this.speedLimit = speedLimit;
    }
    
    private VehicleSpeedData() {
    }
}
