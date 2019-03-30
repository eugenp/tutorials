package com.baeldung.hexagonal.ports;

public interface IOrderNotification {
    void sendNotification(String orderId, String message);
}
