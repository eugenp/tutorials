package com.baeldung.patterns.hexagonalarchitecture.changerequest1rewrite;

public class EmailNotificationImpl implements NotificationService {
    @Override
    public void sendNotification() {
        System.out.println("sending email...");
        // send an email notification to the database team
    }
}
