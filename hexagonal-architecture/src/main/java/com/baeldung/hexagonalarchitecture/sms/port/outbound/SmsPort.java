package com.baeldung.hexagonalarchitecture.sms.port.outbound;

import org.springframework.stereotype.Component;

@Component
public class SmsPort {
    public void send(String phone, String body) {
        System.out.println("Sent sms:" + body);
    }
}
