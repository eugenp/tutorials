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
    private static long DELAY = 1;

    @Test
    public void whenProcessingPayment_thenDelayResponseUsingThreadSleep(){
        when(paymentService.processPayment()).thenAnswer(invocation -> {
            Thread.sleep(DELAY); // Delay of 1 seconds
            return "SUCCESS";
        });

        long startTime = System.currentTimeMillis();
        String result = paymentService.processPayment();
        long endTime = System.currentTimeMillis();

        assertEquals("SUCCESS", result);
        assertTrue((endTime - startTime) >= DELAY); // Verify the delay
    }

    @Test
    public void whenProcessingPayment_thenDelayResponseUsingAnswersWithDelay() throws Exception {

        when(paymentService.processPayment()).thenAnswer(AdditionalAnswers.answersWithDelay(DELAY, invocation -> "SUCCESS"));

        long startTime = System.currentTimeMillis();
        String result = paymentService.processPayment();
        long endTime = System.currentTimeMillis();

        assertEquals("SUCCESS", result);
        assertTrue((endTime - startTime) >= DELAY); // Verify the delay
    }


    @Test
    public void whenProcessingPayment_thenDelayResponseUsingAwaitility() {

        when(paymentService.processPayment()).thenAnswer(invocation -> {
            Awaitility.await().pollDelay(DELAY, TimeUnit.MILLISECONDS).until(()->true);
            return "SUCCESS";
        });

        long startTime = System.currentTimeMillis();
        String result = paymentService.processPayment();
        long endTime = System.currentTimeMillis();

        assertEquals("SUCCESS", result);
        assertTrue((endTime - startTime) >= DELAY); // Verify the delay
    }
}
