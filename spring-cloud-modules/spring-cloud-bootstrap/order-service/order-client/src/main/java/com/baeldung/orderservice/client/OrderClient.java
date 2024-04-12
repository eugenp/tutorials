package com.baeldung.orderservice.client;

public interface OrderClient {

    OrderResponse order(OrderDTO orderDTO);
}
