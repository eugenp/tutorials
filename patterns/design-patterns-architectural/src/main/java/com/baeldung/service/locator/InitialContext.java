package com.baeldung.service.locator;

/**
 * Created by Gebruiker on 4/20/2018.
 */
public class InitialContext {

    public Object lookup(String serviceName) {

        if (serviceName.equalsIgnoreCase("EmailService")) {
            return new EmailService();
        } else if (serviceName.equalsIgnoreCase("SMSService")) {
            return new SMSService();
        }
        return null;
    }
}
