package com.baeldung.hexagonal.domain.port;

import com.baeldung.hexagonal.domain.core.Payment;
import com.baeldung.hexagonal.domain.core.PaymentDetail;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository {
    void save(PaymentDetail payment);
    Optional<PaymentDetail> get(String paymentId);
    List<PaymentDetail> getAll();
}
