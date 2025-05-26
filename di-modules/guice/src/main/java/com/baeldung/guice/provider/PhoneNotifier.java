package com.baeldung.guice.provider;

import java.util.logging.Logger;

import com.google.inject.Provider;

public class PhoneNotifier implements Notifier, Provider<Notifier> {
    private String fromNumber;
    private String toNumber;

    private PhoneNotifier phoneNotifier;
    Logger log = Logger.getLogger(EmailNotifier.class.getName());

    @Override
    public Notifier get() {
        // perform some initialization for email notifier
        this.fromNumber = "baeldung";
        phoneNotifier = new PhoneNotifier();
        return phoneNotifier;
    }

    @Override
    public void sendNotification(String message) {
        log.info("Sending phone notification: " + message);
    }
}
