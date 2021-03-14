package com.baeldung.dddsimplehexagonal.external.driving;

public class OnlineSpeedCameraJsonObj {

    private String registrationPlateNo;
    private float speed;
    private float speedLimit;
    
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
    private OnlineSpeedCameraJsonObj() {
    }
}
