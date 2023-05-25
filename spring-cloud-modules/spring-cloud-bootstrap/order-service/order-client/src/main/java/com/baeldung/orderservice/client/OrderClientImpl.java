package com.baeldung.orderservice.client;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class OrderClientImpl implements OrderClient {

    private RestTemplate restTemplate;

    public OrderClientImpl(RestTemplateBuilder builder) {

        this.restTemplate = builder.build();
    }

    @Override
    public OrderResponse order(OrderDTO orderDTO) {

        String serviceUrl = "http://localhost:8002/order-service";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<OrderDTO> request = new HttpEntity<>(orderDTO, headers);

        OrderResponse orderResponse = restTemplate.postForObject(serviceUrl + "/create", request, OrderResponse.class);

        return orderResponse;

    }
}
