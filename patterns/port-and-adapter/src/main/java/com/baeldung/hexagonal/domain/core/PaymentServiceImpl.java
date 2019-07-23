package com.baeldung.hexagonal.domain.core;

import com.baeldung.hexagonal.domain.port.PaymentRepository;
import com.baeldung.hexagonal.domain.port.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {
    @Inject
    private final PaymentRepository paymentRepository;

    @Override
    public String createPayment(Payment payment) {
        final PaymentDetail paymentDetail = PaymentDetail.of(UUID.randomUUID().toString(),
                payment.getDebtor(), payment.getCreditor(), payment.getAmount(), payment.getCurrency(),
                PaymentDetail.Status.INITIALIZED);
        paymentRepository.save(paymentDetail);
        return paymentDetail.getPaymentId();
    }

    @Override
    public Optional<PaymentDetail> getPaymentDetails(String paymentId) {
        return paymentRepository.get(paymentId);
    }

    @Override
    public List<PaymentDetail> getAllPayments() {
        return paymentRepository.getAll();
    }
}
