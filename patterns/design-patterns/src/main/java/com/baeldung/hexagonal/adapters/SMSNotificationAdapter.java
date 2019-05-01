package com.baeldung.hexagonal.adapters;

import com.baeldung.hexagonal.mocks.SmsProviderApi;
import com.baeldung.hexagonal.ports.NotificationSenderPort;

import java.util.Map;

@SuppressWarnings({ "unused", "SpellCheckingInspection" })
public class SMSNotificationAdapter implements NotificationSenderPort {

    // imaginary api integration for the sake of this example
    private SmsProviderApi api = new SmsProviderApi();

    public void notify(String handle, Map<String, String> data) {
        api.send(handle, data);
    }

}

