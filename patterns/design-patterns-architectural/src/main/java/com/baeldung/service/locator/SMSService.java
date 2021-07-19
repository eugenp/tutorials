package com.baeldung.service.locator;

/**
 * Created by Gebruiker on 4/20/2018.
 */
public class SMSService implements MessagingService {

    public String getMessageBody() {
        return "sms message";
    }

    public String getServiceName() {
        return "SMSService";
    }
}
