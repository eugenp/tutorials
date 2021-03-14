package com.baeldung.dddsimplehexagonal.external.driven;

public class SpeedingFineJsonObj {

    private String registrationPlateNo;
    private float speed;
    private float speedLimit;

    public SpeedingFineJsonObj(
      String registrationPlateNo, float speed, float speedLimit) {

        this.registrationPlateNo = registrationPlateNo;
        this.speed = speed;
        this.speedLimit = speedLimit;
    }

    // getters
    
    public String getRegistrationPlateNo() {
        return registrationPlateNo;
    }

    public float getSpeed() {
        return speed;
    }

    public float getSpeedLimit() {
        return speedLimit;
    }
    
    // private default constructor to prevent creating obj with null state
    private SpeedingFineJsonObj() {
    }
}
