package com.baeldung.async.consumer;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.baeldung.async.producer.OrderProducer;
import com.baeldung.constants.OrderStatus;
import com.baeldung.domain.Order;
import com.baeldung.reactive.repository.OrderRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderConsumer {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderProducer orderProducer;

    @KafkaListener(topics = "orders", groupId = "orders")
    public void consume(Order order) throws IOException {
        log.info("Order received to process: {}", order);
        if (OrderStatus.INITIATION_SUCCESS.equals(order.getOrderStatus())) {
            orderRepository.findById(order.getId())
                .map(o -> {
                    orderProducer.sendMessage(o.setOrderStatus(OrderStatus.RESERVE_INVENTORY));
                    return o.setOrderStatus(order.getOrderStatus())
                        .setResponseMessage(order.getResponseMessage());
                })
                .flatMap(orderRepository::save)
                .subscribe();
        } else if (OrderStatus.INVENTORY_SUCCESS.equals(order.getOrderStatus())) {
            orderRepository.findById(order.getId())
                .map(o -> {
                    orderProducer.sendMessage(o.setOrderStatus(OrderStatus.PREPARE_SHIPPING));
                    return o.setOrderStatus(order.getOrderStatus())
                        .setResponseMessage(order.getResponseMessage());
                })
                .flatMap(orderRepository::save)
                .subscribe();
        } else if (OrderStatus.SHIPPING_FAILURE.equals(order.getOrderStatus())) {
            orderRepository.findById(order.getId())
                .map(o -> {
                    orderProducer.sendMessage(o.setOrderStatus(OrderStatus.REVERT_INVENTORY));
                    return o.setOrderStatus(order.getOrderStatus())
                        .setResponseMessage(order.getResponseMessage());
                })
                .flatMap(orderRepository::save)
                .subscribe();
        } else {
            orderRepository.findById(order.getId())
                .map(o -> {
                    return o.setOrderStatus(order.getOrderStatus())
                        .setResponseMessage(order.getResponseMessage());
                })
                .flatMap(orderRepository::save)
                .subscribe();
        }
    }
}