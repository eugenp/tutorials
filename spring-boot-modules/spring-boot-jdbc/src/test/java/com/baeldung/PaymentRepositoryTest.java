package com.baeldung;

import com.baeldung.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    void givenPayment_whenProcessed_thenTransactionCommits() {
        paymentRepository.processPayment(1L, 200L);
    }
}
