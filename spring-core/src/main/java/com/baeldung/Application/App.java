package com.baeldung.Application;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baeldung.service.CustomerService;

public class App {

    public static void main(String[] args) {

        ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        CustomerService customerService = appContext.getBean("customerService", CustomerService.class);

        System.out.println(customerService.getAllCustomers()
            .get(0)
            .getFirstName());

    }

}
