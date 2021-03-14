package com.baeldung.dddsimplehexagonal.domain.port.incoming;

import java.util.Objects;

public class IncomingSpeedDataDTO {

    private String registrationPlateNo;
    private float speed;
    private float speedLimit;

    public IncomingSpeedDataDTO(String registrationPlateNo, float speed, float speedLimit) {
        this.registrationPlateNo = registrationPlateNo;
        this.speed = speed;
        this.speedLimit = speedLimit;
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

    @Override
    public boolean equals(Object o) {
        
        if (this == o) return true;
        
        if ((o instanceof IncomingSpeedDataDTO) == false) {
            return false;
        }
        
        IncomingSpeedDataDTO dto1 = (IncomingSpeedDataDTO)o;
    
        if (this.registrationPlateNo.equals(dto1.getRegistrationPlateNo()) 
          && (this.speed == dto1.getSpeed())
          && (this.speedLimit == dto1.getSpeedLimit())) {          
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.registrationPlateNo, this.speed, this.speedLimit);
    }
}
