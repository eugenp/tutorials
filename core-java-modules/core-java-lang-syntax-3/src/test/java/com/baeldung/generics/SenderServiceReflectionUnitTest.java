package com.baeldung.generics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SenderServiceReflectionUnitTest {

    @Test
    void givenEmailSender_whenCreateInstanceUsingReflection_thenReturnResult() {
        SenderServiceReflection<EmailSender> service = new SenderServiceReflection<>(EmailSender.class);

        Sender emailSender = service.createInstance();
        String result = emailSender.send();

        assertEquals("EMAIL", result);
    }

}