package com.baeldung.design.hex.adapter.driven.component;

import org.springframework.stereotype.Component;

@Component
public class EmailSenderAPI {
    public void sendEmail(String message) {
        System.out.println(message);
    }
}
