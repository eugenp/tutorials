package com.baeldung.displayallbeans.service;

import org.springframework.stereotype.Service;

@Service
public class FooService {

    public static final String GET_HEADER = "Display All Beans";

    public String getBody() {
        return "This is a sample application that displays all beans " + "in Spring IoC container using ListableBeanFactory interface " + "and Spring Boot Actuators.";
    }

}
