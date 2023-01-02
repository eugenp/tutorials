package com.baeldung.customerservice;

import com.baeldung.orderservice.client.OrderClient;
import com.baeldung.orderservice.client.OrderClientImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;

/**
 * Spring Boot application starter class
 */
@SpringBootApplication
public class CustomerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }

    @Bean
    public OrderClient getOrderClient() {

        return new OrderClientImpl(new RestTemplateBuilder());
    }

}
