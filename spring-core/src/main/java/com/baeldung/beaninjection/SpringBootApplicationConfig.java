package com.baeldung.beaninjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.baeldung.beaninjection.model.Order;
import com.baeldung.beaninjection.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class SpringBootApplicationConfig implements ApplicationRunner {

    @Autowired
    private OrderService orderService;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplicationConfig.class, args);
    }

    @Override
    public void run(ApplicationArguments arg0) throws Exception {
        Order order = orderService.getOrders(123);
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writerWithDefaultPrettyPrinter()
            .writeValueAsString(order));
    }
}
