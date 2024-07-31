package com.baeldung.conditionalonproperty.service;

public class SmsNotification implements NotificationSender {

    @Override
    public String send(String message) {
        return "SMS notification: " + message;
    }

}
