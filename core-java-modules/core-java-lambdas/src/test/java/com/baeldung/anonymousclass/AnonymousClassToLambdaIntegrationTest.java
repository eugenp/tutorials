package com.baeldung.anonymousclass;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AnonymousClassToLambdaIntegrationTest {

    @Test
    public void whenPassingAnonymousClass_thenSuccess() {
        final SenderService emailSenderService = new EmailSenderService();

        final String emailNotif = emailSenderService.callSender(new Sender() {
            @Override
            public String send(String message) {
                return message;
            }
        });

        assertEquals(emailNotif, "Email Notification");
    }

    @Test
    public void whenPassingLambdaExpression_thenSuccess() {
        final SenderService smsSenderService = new SmsSenderService();

        final String smsNotif = smsSenderService.callSender((String message) -> {
            return message;
        });

        assertEquals(smsNotif, "SMS Notification");
    }

}
