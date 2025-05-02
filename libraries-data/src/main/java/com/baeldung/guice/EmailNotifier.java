package com.baeldung.guice;

import com.google.inject.Provider;

public class EmailNotifier implements Notifier, Provider<Notifier> {

    private String smtpUrl;
    private String user;
    private String password;
    private EmailNotifier emailNotifier;

    @Override
    public Notifier get() {
        // perform some initialization for email notifier
        this.smtpUrl = "smtp://localhost:25";
        emailNotifier = new EmailNotifier();
        return emailNotifier;
    }

    @Override
    public void sendNotification(String message) {
        System.out.println("Sending email notification: " + message);
    }
}
