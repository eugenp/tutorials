package com.baeldung.beaninjection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.beaninjection.service.CustomerService;
import com.baeldung.beaninjection.service.OffersService;
import com.baeldung.beaninjection.service.OrderService;

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
        orderService.setOffersService(offersService);// setter injection
        return orderService;
    }

}
