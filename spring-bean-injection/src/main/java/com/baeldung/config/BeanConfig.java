package com.baeldung.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.service.CustomerService;
import com.baeldung.service.OffersService;
import com.baeldung.service.OrderService;

@Configuration
public class BeanConfig {

    @Bean
    public CustomerService customerService() {
        return new CustomerService();
    }

    @Bean
    public OffersService offersService() {
        return new OffersService();
    }

    @Bean
    public OrderService orderService(CustomerService customerService, OffersService offersService) {
        OrderService orderService = new OrderService(customerService);// constructor injection
        orderService.setOffersService(offersService);//setter injection
        return orderService;
    }

}
