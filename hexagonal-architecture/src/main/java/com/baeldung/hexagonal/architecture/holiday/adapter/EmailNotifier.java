package com.baeldung.hexagonal.architecture.holiday.adapter;

import com.baeldung.hexagonal.architecture.holiday.port.Notifier;

public class EmailNotifier implements Notifier {

    @Override
    public void sendMessage(String recipient, String message) {
        // logic related to email notification
    }
}
