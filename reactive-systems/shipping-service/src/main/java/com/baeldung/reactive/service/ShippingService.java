package com.baeldung.reactive.service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.constants.OrderStatus;
import com.baeldung.domain.Order;
import com.baeldung.domain.Shipment;
import com.baeldung.reactive.repository.ShipmentRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ShippingService {

    @Autowired
    ShipmentRepository shipmentRepository;

    public Mono<Order> handleOrder(Order order) {
        log.info("Handle order invoked with: {}", order);
        return Mono.just(order)
            .flatMap(o -> {
                Date shippingDate = null;
                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                if (hour <= 24 && hour >= 0) {
                    cal.add(Calendar.DATE, 1);
                    shippingDate = cal.getTime();
                } else {
                    throw new RuntimeException("The current time is off the limits to place order.");
                }
                return shipmentRepository.save(new Shipment().setAddress(order.getShippingAddress())
                    .setShippingDate(shippingDate));
            })
            .map(s -> order.setShippingDate(s.getShippingDate())
                .setOrderStatus(OrderStatus.SUCCESS));
    }

}