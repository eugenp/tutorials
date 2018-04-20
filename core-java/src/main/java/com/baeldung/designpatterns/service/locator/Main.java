package com.baeldung.designpatterns.service.locator;

/**
 * Created by Gebruiker on 4/20/2018.
 */
public class Main {

    public static void main(String[] args) {
        MessagingService service = ServiceLocator.getService("EmailService");
        service.getMessageBody();
    }
}
