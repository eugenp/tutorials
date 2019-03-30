package com.baeldung.service.locator;

/**
 * Created by Gebruiker on 4/20/2018.
 */
public class Main {

    public static void main(String[] args) {

        MessagingService service = ServiceLocator.getService("EmailService");
        String email = service.getMessageBody();
        System.out.println(email);

        service = ServiceLocator.getService("SMSService");
        String sms = service.getMessageBody();
        System.out.println(sms);

        service = ServiceLocator.getService("EmailService");
        String newEmail = service.getMessageBody();
        System.out.println(newEmail);
    }
}
