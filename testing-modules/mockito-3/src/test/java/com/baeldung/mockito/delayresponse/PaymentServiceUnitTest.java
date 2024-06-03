package com.baeldung.mockito.delayresponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.concurrent.TimeUnit;

import org.awaitility.Awaitility;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PaymentServiceUnitTest {

    @Mock
    PaymentService paymentService;

    @Test
    public void whenProcessingPayment_thenDelayResponseUsingThreadSleep(){
        when(paymentService.processPayment()).thenAnswer(invocation -> {
            Thread.sleep(2000); // Delay of 2 seconds
            return "SUCCESS";
        });

        long startTime = System.currentTimeMillis();
        String result = paymentService.processPayment();
        long endTime = System.currentTimeMillis();

        assertEquals("SUCCESS", result);
        assertTrue((endTime - startTime) >= 2000); // Verify the delay
    }

    @Test
    public void whenProcessingPayment_thenDelayResponseUsingAnswersWithDelay() throws Exception {

        when(paymentService.processPayment()).thenAnswer(AdditionalAnswers.answersWithDelay(2000, invocation -> "SUCCESS"));

        long startTime = System.currentTimeMillis();
        String result = paymentService.processPayment();
        long endTime = System.currentTimeMillis();

        assertEquals("SUCCESS", result);
        assertTrue((endTime - startTime) >= 2000); // Verify the delay
    }


    @Test
    public void whenProcessingPayment_thenDelayResponseUsingAwaitility() {

        when(paymentService.processPayment()).thenAnswer(invocation -> {
            Awaitility.await().pollDelay(2, TimeUnit.SECONDS).until(()->true);
            return "SUCCESS";
        });

        long startTime = System.currentTimeMillis();
        String result = paymentService.processPayment();
        long endTime = System.currentTimeMillis();

        assertEquals("SUCCESS", result);
        assertTrue((endTime - startTime) >= 2000); // Verify the delay
    }
}
