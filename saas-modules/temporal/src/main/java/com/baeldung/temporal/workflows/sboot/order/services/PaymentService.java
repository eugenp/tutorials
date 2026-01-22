package com.baeldung.temporal.workflows.sboot.order.services;

import com.baeldung.temporal.workflows.sboot.order.domain.PaymentAuthorization;
import com.baeldung.temporal.workflows.sboot.order.domain.RefundRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Mock payment service used for integration tests
 */
@Service
public class PaymentService {
    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);

    public PaymentAuthorization processPaymentRequest(PaymentAuthorization paymentAuthorization) {
        log.info("Processing Payment Request");
        return paymentAuthorization;
    }

    public RefundRequest createRefundRequest(PaymentAuthorization payment) {
        log.info("Processing Refund Request");
        return new RefundRequest(payment);
    }
}
