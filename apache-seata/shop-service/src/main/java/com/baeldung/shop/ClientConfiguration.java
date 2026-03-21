package com.baeldung.shop;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientConfiguration {

    @Bean
    public Client inventoryServiceClient(RestClient restClient,
        @Value("${inventory_service.url}/inventory/{mode}") String url) {
        return new Client(url, restClient);
    }

    @Bean
    public Client orderServiceClient(RestClient restClient,
        @Value("${order_service.url}/order/{mode}") String url) {
        return new Client(url, restClient);
    }

    @Bean
    public Client billingServiceClient(RestClient restClient,
        @Value("${billing_service.url}/billing/{mode}") String url) {
        return new Client(url, restClient);
    }
}
