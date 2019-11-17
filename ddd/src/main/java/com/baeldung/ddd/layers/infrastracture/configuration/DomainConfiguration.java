package com.baeldung.ddd.layers.infrastracture.configuration;

import com.baeldung.ddd.layers.DomainLayerApplication;
import com.baeldung.ddd.layers.domain.repository.OrderRepository;
import com.baeldung.ddd.layers.domain.service.DomainOrderService;
import com.baeldung.ddd.layers.domain.service.OrderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = DomainLayerApplication.class)
public class DomainConfiguration {

    @Bean
    OrderService orderService(final OrderRepository orderRepository) {
        return new DomainOrderService(orderRepository);
    }
}
