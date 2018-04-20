package com.baeldung.designpatterns.service.locator;

/**
 * Created by Gebruiker on 4/20/2018.
 */
public class InitialContext {

    public Object lookup(String jndiName) {

        if (jndiName.equalsIgnoreCase("EmailService")) {
            return new EmailService();
        } else if (jndiName.equalsIgnoreCase("SMSService")) {
            return new SMSService();
        }
        return null;
    }
}
