package org.baeldung.beaninjection;

import org.springframework.stereotype.Component;

@Component
public class EmailNotification implements Notification {

    @Override
    public void send(String message) {
        System.out.println("Sending email with the message '" + message + "'");
    }

}
