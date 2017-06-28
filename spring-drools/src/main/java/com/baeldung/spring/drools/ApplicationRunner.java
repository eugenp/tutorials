package com.baeldung.spring.drools;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.baeldung.spring.drools.model.Order;
import com.baeldung.spring.drools.model.Result;
import com.baeldung.spring.drools.service.OrderService;
import com.baeldung.spring.drools.service.OrderServiceConfiguration;

public class ApplicationRunner {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(OrderServiceConfiguration.class);
        OrderService orderService = (OrderService) context.getBean(OrderService.class);

        Order order = new Order();
        order.setNightFlag(true);
        order.setServiceName("Cab");
        order.setServiceType("Transport");
        order.setTimeInMinutes(90L);
        Result result = new Result();
        orderService.calculateBilling(order, result);
    }

}
