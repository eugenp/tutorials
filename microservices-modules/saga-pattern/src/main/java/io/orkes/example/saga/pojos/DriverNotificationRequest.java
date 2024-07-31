package io.orkes.example.saga.pojos;

import lombok.Data;

@Data
public class DriverNotificationRequest {
    int driverId;
    String dropOff;
    String pickUp;
    String orderId;
}
