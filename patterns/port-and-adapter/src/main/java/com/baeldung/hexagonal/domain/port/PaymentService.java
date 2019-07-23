package com.baeldung.hexagonal.domain.port;

import com.baeldung.hexagonal.domain.core.Payment;
import com.baeldung.hexagonal.domain.core.PaymentDetail;

import java.util.List;
import java.util.Optional;

public interface PaymentService {

    String createPayment(Payment payment);
    Optional<PaymentDetail> getPaymentDetails(String paymentId);
    List<PaymentDetail> getAllPayments();
}
