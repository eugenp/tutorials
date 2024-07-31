package com.baeldung.spring.cloud.aws.sqs.acknowledgement.service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.baeldung.spring.cloud.aws.sqs.acknowledgement.model.OrderStatus;

@Service
public class OrderService {

    Map<UUID, OrderStatus> ORDER_STATUS_STORAGE = new ConcurrentHashMap<>();

    public void updateOrderStatus(UUID orderId, OrderStatus status) {
        ORDER_STATUS_STORAGE.put(orderId, status);
    }

    public OrderStatus getOrderStatus(UUID orderId) {
        return ORDER_STATUS_STORAGE.getOrDefault(orderId, OrderStatus.UNKNOWN);
    }

}
