package com.baeldung.ddd.layers.infrastracture.configuration;

import com.baeldung.ddd.layers.domain.repository.OrderRepository;
import com.baeldung.ddd.layers.domain.service.DomainOrderService;
import com.baeldung.ddd.layers.domain.service.OrderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfiguration {

    @Bean
    OrderService orderService(final OrderRepository orderRepository) {
        return new DomainOrderService(orderRepository);
    }
}
