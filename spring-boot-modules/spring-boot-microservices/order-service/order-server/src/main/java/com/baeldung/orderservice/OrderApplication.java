package com.baeldung.orderservice;

import com.baeldung.paymentservice.PaymentClient;
import com.baeldung.paymentservice.PaymentClientImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;

/**
 * Spring Boot application starter class
 */
@SpringBootApplication
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

    @Bean
    public PaymentClient getPaymentClient() {

        return new PaymentClientImpl(new RestTemplateBuilder());
    }
}
