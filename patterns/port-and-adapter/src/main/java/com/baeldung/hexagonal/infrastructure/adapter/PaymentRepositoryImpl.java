package com.baeldung.hexagonal.infrastructure.adapter;

import com.baeldung.hexagonal.domain.core.PaymentDetail;
import com.baeldung.hexagonal.domain.port.PaymentRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {
    private final Map<String, PaymentDetail> paymentStore = new ConcurrentHashMap<>();

    @Override
    public void save(PaymentDetail payment) {
        paymentStore.put(payment.getPaymentId(), payment);
    }

    @Override
    public Optional<PaymentDetail> get(String paymentId) {
        return Optional.ofNullable(paymentStore.get(paymentId));
    }

    @Override
    public List<PaymentDetail> getAll() {
        return new ArrayList<>(paymentStore.values());
    }
}
