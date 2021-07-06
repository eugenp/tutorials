package com.baeldung.hexagonalarchitecture.email.port.outbound;

import org.springframework.stereotype.Component;

@Component
public class EmailPort {
    public void send(String email, String body) {
        System.out.println("Sent email:" + body);
    }
}
