package com.baeldung.dapr.pubsub.publisher;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.dapr.spring.messaging.DaprMessagingTemplate;

@RestController
public class OrdersRestController {

    private static final Logger logger = LoggerFactory.getLogger(OrdersRestController.class);

    private List<Order> repository = new ArrayList<>();

    private DaprMessagingTemplate<Order> messaging;
    
    public OrdersRestController(DaprMessagingTemplate<Order> messagingTemplate){
        this.messaging = messagingTemplate;
    }

    @PostMapping("/orders")
    public String storeOrder(@RequestBody Order order) {
        repository.add(order);

        logger.info("[bael] Publishing Order Event: {}", order);
        messaging.send("topic", order);
        return "Order Stored and Event Published";
    }

    @GetMapping("/orders")
    public Iterable<Order> getAll() {
        return repository;
    }

    @GetMapping("/orders/byItem/")
    public Iterable<Order> getAllByItem(@RequestParam("item") String item) {
        return repository.stream().filter(order -> item.equals(order.getItem())).collect(Collectors.toList());
    }

    @GetMapping("/orders/byAmount/")
    public Iterable<Order> getAllByItem(@RequestParam("amount") Integer amount) {
        return repository.stream().filter(order -> amount.equals(order.getAmount())).collect(Collectors.toList());
    }
}
