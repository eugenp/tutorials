package com.baeldung.hexagonal.application.adapter;

import com.baeldung.hexagonal.domain.core.Payment;
import com.baeldung.hexagonal.domain.core.PaymentDetail;
import com.baeldung.hexagonal.domain.port.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    public String createPayment(@RequestBody Payment payment) {
        return paymentService.createPayment(payment);
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentDetail> getPayment(@PathVariable String paymentId) {
        return ResponseEntity.of(paymentService.getPaymentDetails(paymentId));
    }

    @GetMapping("/all")
    public List<PaymentDetail> getAllPayment() {
        return paymentService.getAllPayments();
    }
}
