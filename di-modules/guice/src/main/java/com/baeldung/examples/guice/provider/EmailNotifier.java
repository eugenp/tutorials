package com.baeldung.examples.guice.provider;

import com.google.inject.Provider;
import java.util.logging.Logger;

public class EmailNotifier implements Notifier, Provider<Notifier> {

    private String smtpUrl;
    private String user;
    private String password;
    private EmailNotifier emailNotifier;
    Logger log = Logger.getLogger(EmailNotifier.class.getName());

    @Override
    public Notifier get() {
        // perform some initialization for email notifier
        this.smtpUrl = "smtp://localhost:25";
        emailNotifier = new EmailNotifier();
        return emailNotifier;
    }

    @Override
    public void sendNotification(String message) {
        log.info("Sending email notification: " + message);
    }
}