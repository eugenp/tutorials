package com.baeldung.spring.kafka.delay;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.listener.KafkaBackoffException;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class OrderListener {

    private final OrderService orderService;

    private final ObjectMapper objectMapper;

    public OrderListener(OrderService orderService, ObjectMapper objectMapper) {
        this.orderService = orderService;
        this.objectMapper = objectMapper;
    }

    @RetryableTopic(attempts = "1", include = KafkaBackoffException.class, dltStrategy = DltStrategy.NO_DLT)
    @KafkaListener(topics = { "web.orders", "web.internal.orders" }, groupId = "orders")
    public void handleOrders(String order) throws JsonProcessingException {
        Order orderDetails = objectMapper.readValue(order, Order.class);
        OrderService.Status orderStatus = orderService.findStatusById(orderDetails.getOrderId());
        if (orderStatus.equals(OrderService.Status.ORDER_CONFIRMED)) {
            orderService.processOrder(orderDetails);
        }
    }

}
