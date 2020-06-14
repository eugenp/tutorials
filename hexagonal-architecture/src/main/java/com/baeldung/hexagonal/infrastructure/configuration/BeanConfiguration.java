package com.baeldung.hexagonal.infrastructure.configuration;

import com.baeldung.hexagonal.domain.repository.OrderRepository;
import com.baeldung.hexagonal.domain.service.DomainOrderService;
import com.baeldung.hexagonal.domain.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Autowired
    private OrderRepository orderRepository;

    @Bean
    OrderService orderService() {
        DomainOrderService domainOrderService = new DomainOrderService();
        domainOrderService.setOrderRepository(this.orderRepository);
        return domainOrderService;
    }
}
