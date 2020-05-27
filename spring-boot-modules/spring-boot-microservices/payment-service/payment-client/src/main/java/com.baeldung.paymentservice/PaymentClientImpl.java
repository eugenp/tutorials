package com.baeldung.paymentservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PaymentClientImpl implements PaymentClient {

    private RestTemplate restTemplate;

    public PaymentClientImpl(RestTemplateBuilder builder) {

        this.restTemplate = builder.build();
    }

    @Override
    public PaymentResponse pay(String orderNumber, PaymentDTO paymentDTO) {

        String serviceUrl = "http://localhost:8003/payment-service";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PaymentDTO> request = new HttpEntity<>(paymentDTO, headers);

        PaymentResponse paymentResponse = restTemplate.postForObject(serviceUrl + "/pay/" + orderNumber, request, PaymentResponse.class);

        return paymentResponse;

    }
}
