package com.baeldung.dddsimplehexagonal.external.driven;

public interface PoliceSpeedingOffenseNotifInterface {
    static final String ITEM_DELIMITER = "%";
    
    static String createSpeedingOffenseNotifData(String registrationPlateNo, float speed, float speedLimit) {
        String notificationData = registrationPlateNo + ITEM_DELIMITER + speed + ITEM_DELIMITER + speedLimit;
        return notificationData;
    }

    void sendSpeedingOffenseNotification(String notificationData);
}
