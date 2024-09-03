package com.baeldung.exceptionhandling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Override
    public void validatePaymentDetails(String paymentDetails) {

        if (paymentDetails == null || paymentDetails.isEmpty()) {
            throw new IllegalArgumentException("Payment details cannot be null or empty.");
        }

        logger.info("Payment details validated successfully.");
    }

    @Override
    public void processPayment(String paymentDetails) {

        if ("INVALID".equals(paymentDetails)) {
            throw new RuntimeException("Payment processing failed due to invalid details.");
        }

        logger.info("Payment processed successfully.");
    }
}
