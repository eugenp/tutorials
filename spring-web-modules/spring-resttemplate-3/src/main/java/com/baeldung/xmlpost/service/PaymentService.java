package com.baeldung.xmlpost.service;

import com.baeldung.xmlpost.model.PaymentRequest;
import com.baeldung.xmlpost.model.PaymentResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class PaymentService {
    private final RestTemplate restTemplate;

    public PaymentService(@Qualifier("xmlRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public PaymentResponse processPayment(PaymentRequest request, String paymentUrl) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_XML);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_XML));

            HttpEntity<PaymentRequest> entity = new HttpEntity<>(request, headers);

            ResponseEntity<PaymentResponse> response =
              restTemplate.postForEntity(paymentUrl, entity, PaymentResponse.class);

            return response.getBody();
        } catch (Exception ex) {
            throw new RuntimeException("Payment processing failed: " + ex.getMessage(), ex);
        }
    }
}

