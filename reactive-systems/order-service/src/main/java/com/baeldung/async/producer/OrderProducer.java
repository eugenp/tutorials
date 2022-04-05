package com.baeldung.async.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.baeldung.domain.Order;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderProducer {

    @Autowired
    private KafkaTemplate<String, Order> kafkaTemplate;

    public void sendMessage(Order order) {
        log.info("Order processed to dispatch: {}", order);
        this.kafkaTemplate.send("orders", order);
    }

}
