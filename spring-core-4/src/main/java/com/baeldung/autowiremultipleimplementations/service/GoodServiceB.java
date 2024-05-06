package com.baeldung.autowiremultipleimplementations.service;

import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Profile("default")
@Order(2)
public class GoodServiceB implements GoodService {

    @Override
    public String getHelloMessage() {
        return "Hello from B!";
    }
}
