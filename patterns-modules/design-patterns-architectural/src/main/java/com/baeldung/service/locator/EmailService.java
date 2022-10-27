package com.baeldung.service.locator;

/**
 * Created by Gebruiker on 4/20/2018.
 */
public class EmailService implements MessagingService {

    public String getMessageBody() {
        return "email message";
    }

    public String getServiceName() {
        return "EmailService";
    }
}
