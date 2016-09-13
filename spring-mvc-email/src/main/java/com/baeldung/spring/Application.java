package com.baeldung.spring;

import com.baeldung.spring.mail.EmailService;
import com.baeldung.spring.mail.EmailServiceImpl;
import com.baeldung.spring.mail.Order;
import com.baeldung.spring.mail.OrderManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;
import org.springframework.mail.SimpleMailMessage;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    public EmailService emailService;

    @Autowired
    public OrderManager orderManager;

    @PostConstruct
    public void postConstruct() {
        Order order = new Order("user_email_address", "First Name", "Last Name");
        orderManager.placeOrder(order);
    }
}
