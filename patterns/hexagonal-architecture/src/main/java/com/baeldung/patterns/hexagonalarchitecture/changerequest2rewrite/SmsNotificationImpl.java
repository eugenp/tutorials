package com.baeldung.patterns.hexagonalarchitecture.changerequest2rewrite;

public class SmsNotificationImpl implements NotificationService {
    @Override
    public void sendNotification() {
        System.out.println("sending sms...");
        // send an sms notification to the database team
    }
}
